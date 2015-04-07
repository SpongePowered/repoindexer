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

import com.google.common.collect.Lists;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenCoordinate;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepository;

import java.util.List;

/**
 * Created by progwml6 on 4/6/15.
 */
public class ResolvedArtifact {
    public ResolvedArtifact (MavenRemoteRepository repo, MavenResolvedArtifact main) {
        this.resolvedArtifactClassifiers = Lists.newArrayList();
        this.repo = repo;
        this.resolvedArtifactMain = main;
    }
    public ResolvedArtifact (MavenRemoteRepository repo, MavenResolvedArtifact main, List<MavenResolvedArtifact> classifiers) {
        this.resolvedArtifactClassifiers = classifiers;
        this.repo = repo;
        this.resolvedArtifactMain = main;
    }

    public MavenRemoteRepository repo;
    public MavenResolvedArtifact resolvedArtifactMain;
    public List<MavenResolvedArtifact> resolvedArtifactClassifiers;

    public String getDescription() {
        return resolvedArtifactMain.getCoordinate().getArtifactId();
    }
    public SuperStringPair getMainDownloadInfo() {
        List<StringPair> ls = Lists.newArrayList();
        StringPair spair;
        String version;
        String urlBase = repo.getUrl();
        MavenCoordinate mc = resolvedArtifactMain.getCoordinate();
        version = resolvedArtifactMain.getResolvedVersion();
        spair = new StringPair("main", EntryPoint.getArtifactUrl(urlBase, resolvedArtifactMain));
        for (MavenResolvedArtifact ra : resolvedArtifactClassifiers) {
            mc = ra.getCoordinate();
            String id = mc.getType().getExtension();
            if(mc.getClassifier() !=null && !mc.getClassifier().isEmpty()) {
                id = mc.getClassifier();
            }
            ls.add(new StringPair(id, EntryPoint.getArtifactUrl(urlBase, ra)));
        }
        return new SuperStringPair(version, spair, ls);
    }

}
