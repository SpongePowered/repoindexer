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

import java.util.List;

/**
 * Created by progwml6 on 4/6/15.
 */
public class Artifact {
    private Repo repo;
    private String groupId, artifactId;
    private List<Variation> variations;
    public Artifact(Repo repo, String groupId, String artifactId) {
        variations = Lists.newArrayList();
        this.repo = repo;
        this.groupId = groupId;
        this.artifactId = artifactId;
    }
    public void setRepo(Repo r) {
        this.repo = r;
    }
    public void addVariation(Variation v) {
        variations.add(v);
    }
    public List<Variation> getVariations() {
        return variations;
    }
    public Repo getRepo() {
        return this.repo;
    }
    public String getGroupId(){
        return this.groupId;
    }
    public void setGroupId(String id) {
        this.groupId = id;
    }
    public String getArtifactId(){
        return this.artifactId;
    }
    public void setArtifactId(String id) {
        this.artifactId = id;
    }

}
