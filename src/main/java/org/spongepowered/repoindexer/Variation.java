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

import com.google.common.base.Optional;

import javax.annotation.Nullable;

/**
 * Created by progwml6 on 4/6/15.
 */
public class Variation {

    public static Variation SOURCES_JAR = new Variation("sources", "jar");
    public static Variation JAVADOC_JAR = new Variation("javadoc", "jar");

    /**
     *
     * @param classifier the classifier of the artifact if used
     * @param extension the extension of the artifact(jar by default)
     */
    public Variation(@Nullable String classifier, @Nullable String extension) {
        this.ext = extension;
        if(ext == null)
            this.ext = "jar";
        this.classifier = classifier;
    }

    /**
     *
     * @param classifier the classifier of the artifact if used
     * @param extension the extension of the artifact(jar by default)
     * @param after the first version that has this artifact type
     */
    public Variation(@Nullable String classifier, @Nullable String extension, @Nullable String after) {
        this(classifier, extension);
        this.after = Optional.fromNullable(after);
    }
    public String ext;
    public String classifier;
    public Optional<String> after;
}
