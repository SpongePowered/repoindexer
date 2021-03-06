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

import com.beust.jcommander.Parameter;

/**
 * Created by progwml6 on 4/7/15.
 */
public class Cmd {

    private static Cmd instance;

    public static Cmd getInstance() {
        return instance;
    }

    static {
        instance = new Cmd();
    }

    @Parameter(names = {"--user", "-u"}, description = "username")
    public String user = null;

    @Parameter(names = {"--pass", "-p"}, description = "pass")
    public String pass = null;

    @Parameter(names = {"--ftpurl", "-l"}, description = "ftpurl")
    public String url = null;

    @Parameter(names = {"--key", "-k"}, description = "s3 key")
    public String key = null;

    @Parameter(names = {"--secure-key", "-s"}, description = "s3 secure key")
    public String securekey = null;

    @Parameter(names = {"--bucket", "-t"}, description = "s3 bucket")
    public String bucket = null;

    @Parameter(names = {"--region", "-r"}, description = "s3 region")
    public String region = null;

    @Parameter(names = {"--localLocation","-o"}, description = "local location to copy index file to")
    public String localLoc = null;

    @Parameter(names = {"--ftpmode", "-f"}, description = "true = FTP false=sftp", arity = 1)
    public boolean ftpmode = true;

    @Parameter(names = {"--base", "-b"}, description = "base location to deploy in ftp/sftp repo")
    public String base = "";

    @Parameter(names = {"--index", "-i"}, description = "location of index.mustache file")
    public String loc = "index.mustache";

    @Parameter(names = {"--mavenrepo", "-m"}, description = "maven repo url")
    public String mavenrepo = "http://repo.spongepowered.org/maven/";

    @Parameter(names = {"--additionalRepos"}, description = "additional maven repos to use for dependency resolution use ^ to seperate")
    public String additionalRepos = null;

    @Parameter(names = {"--excludes", "-x"}, description = "allows specifying certain artifacts that should be excluded from transitive resolution in the tool group:artifact seperated by ^")
    public String exclude = null;

    @Parameter(names = {"--coord", "-c"}, description = "maven coordinate in gradle format NO VERSION, classifier, or extension")
    public String mavencoord = "org.spongepowered:sponge";

    @Parameter(names = {"--extra", "-e"}, description = " classifier^extension*after seperated by % use the text 'null' for null data, can add *VERSION to specify that something only exists after that version")
    public String extra = "sources^jar%javadoc^jar";

    @Parameter(names = {"--deployFile", "-d"}, description = "change the filename to deploy to")
    public String deployFile = "index.html";
}
