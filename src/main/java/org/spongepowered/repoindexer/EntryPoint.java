/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.repoindexer;

import com.beust.jcommander.JCommander;
import com.google.common.collect.Lists;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenCoordinate;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepositories;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepository;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenUpdatePolicy;
import org.spongepowered.repoindexer.mavenmeta.Metadata;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Created by progwml6 on 4/5/15.
 */
public class EntryPoint {

    private static MavenRemoteRepository mvnremote;
    private static List<ResolvedArtifact> artifactslist;

    public static void main(String args[]) {
        try {
            JCommander jc = new JCommander(Cmd.getInstance(), args);
            Artifact artifact;
            artifactslist = Lists.newArrayList();
            String[] repostuff = Cmd.getInstance().mavencoord.split(":");
            artifact = new Artifact(new Repo(Cmd.getInstance().mavenrepo), repostuff[0], repostuff[1]);
            String[] sets = Cmd.getInstance().extra.split("%");
            for (String s : sets) {
                System.out.println(s);
                String[] tmp = s.split("\\^");
                if (tmp.length > 1) {
                    if (tmp[0].equals("null")) {
                        tmp[0] = null;
                    }
                    if (tmp[1].equals("null")) {
                        tmp[1] = null;
                    }
                    artifact.addVariation(new Variation(tmp[0], tmp[1]));
                    System.out.println("added variation " + tmp[0] + " " + tmp[1]);
                }
            }
            //artifact.addVariation(new Variation(null, "exe"));
            //artifact.addVariation(new Variation(null, "zip"));
            //File location = new File("/Users/progwml6/repos/mc/repoindex/src/main/dltemplates/index.mustache");
            File out = new File("build/repoindexer/index.html");
            if (!out.getParentFile().exists()) {
                out.getParentFile().mkdirs();
            }
            process(new File(Cmd.getInstance().loc), out, artifact, Cmd.getInstance().url, Cmd.getInstance().user, Cmd.getInstance().pass,
                    Cmd.getInstance().base,
                    FTPType.FTP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void process(File input, File output, Artifact artifact, String ftpUrl, String user, String pass, String remotebase, FTPType type)
            throws IOException, JAXBException {
        artifactslist = Lists.newArrayList();
        mvnremote = MavenRemoteRepositories.createRemoteRepository("UserRemoteRepo", artifact.getRepo().getUrl(), "default").setUpdatePolicy(
                MavenUpdatePolicy.UPDATE_POLICY_NEVER);
        System.out.println("using repo: " + mvnremote.getUrl());
        System.out.println();
        String xml = getIndex(artifact);
        System.out.println(xml);
        Metadata md = parsemavenmeta(xml);
        debugmeta(md);
        getAllVersionPoms(md, artifact);
        System.out.println("generating page");
        WebHandler.createWebpage(input, output, artifact.getArtifactId(), artifactslist, artifact.getArtifactId());
        System.out.println("uploading if needed");
        if (Cmd.getInstance().url != null) {
            upload(output, ftpUrl, user, pass,
                   remotebase + artifact.getGroupId().replace(".", "/") + "/" + artifact.getArtifactId().replace(".", "/") + "/index.html", type);
        }
    }

    public static void upload(File toUpload, String ftpUrl, String user, String pass, String remoteloc, FTPType type) {
        if (type == FTPType.FTP) {
            FTPUtils.uploadFTP(user, pass, ftpUrl, toUpload, remoteloc);
        } else {
            FTPUtils.uploadSFTP(user, pass, ftpUrl, toUpload, remoteloc);
        }
    }

    private static String getIndex(Artifact artifact) throws IOException {

        URL u = new URL(artifact.getRepo().getUrl() + artifact.getGroupId().replace(".", "/") + "/" + artifact.getArtifactId().replace(".", "/") + "/"
                        + "maven-metadata.xml");
        System.out.println("Downloading " + u.toExternalForm());
        return IOUtils.toString(u, Charsets.UTF_8);
    }

    private static Metadata parsemavenmeta(String d) throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance("org.spongepowered.repoindexer.mavenmeta");
        Unmarshaller u = jc.createUnmarshaller();
        return (Metadata) u.unmarshal(IOUtils.toInputStream(d, "UTF-8"));

    }

    private static void getAllVersionPoms(Metadata md, Artifact artifact) throws IOException, JAXBException {
        List<String> ls = Lists.newArrayList();
        for (String s : md.versioning.versions.version) {
            List<MavenResolvedArtifact>
                    mra =
                    Maven.configureResolver().withRemoteRepo(mvnremote).withMavenCentralRepo(false)
                            .resolve(artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + s).withoutTransitivity().asList(
                            MavenResolvedArtifact.class);
            for (MavenResolvedArtifact mr : mra) {
                ResolvedArtifact resolved = new ResolvedArtifact(mvnremote, mr);
                try {
                    //ls.add(mr.getResolvedVersion() + " artifacturl: " + mr.as(URL.class));
                    for (Variation v : artifact.getVariations()) {
                        String classifier = "";
                        try {
                            if (v.classifier != null && !v.classifier.isEmpty()) {
                                classifier = ":" + v.classifier;
                            }
                            List<MavenResolvedArtifact>
                                    mraInner =
                                    Maven.configureResolver().withRemoteRepo(mvnremote).withMavenCentralRepo(false)
                                            .resolve(artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + v.ext + classifier + ":" + s)
                                            .withoutTransitivity()
                                            .asList(MavenResolvedArtifact.class);
                            for (MavenResolvedArtifact mr2 : mraInner) {
                                resolved.resolvedArtifactClassifiers.add(mr2);
                                //ls.add("artifacturl(classifier): " + mr2.as(URL.class));
                            }
                        } catch (Exception e) {
                            //System.out.println("issue with classifier " + classifier.replace(":", "") + " ext " + v.ext + " artifact " + mr.as(URL.class));
                            e.printStackTrace();
                        }
                    }
                    artifactslist.add(resolved);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
        for (String s : ls) {
            System.out.println(s);
        }

    }


    public static void debugmeta(Metadata md) {
        List<String> vl = md.versioning.versions.version;
        System.out.println("Artifacts last updated " + md.versioning.lastUpdated + " : ");
        for (String s : vl) {
            System.out.println("  " + s);
        }
        System.out.println();

    }

    public static String getArtifactUrl(String url, MavenResolvedArtifact artifact) {
        String resolved = artifact.getResolvedVersion();
        MavenCoordinate mc = artifact.getCoordinate();
        if (mc.getClassifier() == null || mc.getClassifier().equals("")) {
            return url + mc.getGroupId().replace(".", "/") + "/" + mc.getArtifactId().replace(".", "/") + "/" + mc.getVersion() + "/" + mc
                    .getArtifactId() + "-" + resolved
                   + "." + mc.getPackaging().getExtension();
        } else {
            return url + mc.getGroupId().replace(".", "/") + "/" + mc.getArtifactId().replace(".", "/") + "/" + mc.getVersion() + "/" + mc
                    .getArtifactId() + "-" + resolved
                   + "-" + mc.getClassifier()
                   + "." + mc.getPackaging().getExtension();
        }
    }
}
