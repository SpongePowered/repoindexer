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

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MustacheData {

    public MustacheData(String title, List<SuperStringPair> superStringPairs) {
        mfiles = Lists.newArrayList();
        //System.out.println(ra.size());
        System.out.println(superStringPairs.size());
        this.title = title;
        for (SuperStringPair ssp: superStringPairs){
            mfiles.add(new MFile(ssp));
        }
        mfiles = Lists.reverse(mfiles);
        System.out.println(mfiles.size());
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("en", "EN"));
        date = df.format (new Date ());
    }

    String title;
    String date;
    List<MFile> mfiles;

    static class MFile {

        MFile(SuperStringPair ssp) {

            this.mainDescription = ssp.main.description;
            this.mainUrl = ssp.main.url;
            this.version = ssp.version;
            entries = Lists.newArrayList();
            for (StringPair sp : ssp.sub) {
                entries.add(new MEntry(sp));
            }
        }

        String version;
        String mainDescription;
        String mainUrl;
        List<MEntry> entries;
    }

    static class MEntry {

        MEntry(StringPair sp) {
            this.description = sp.description;
            this.url = sp.url;
        }

        String description;
        String url;
    }


}
