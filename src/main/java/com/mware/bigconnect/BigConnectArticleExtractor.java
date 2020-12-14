/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mware.bigconnect;

import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.extractors.ArticleExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class BigConnectArticleExtractor {
    private String url;
    private String html;

    public BigConnectArticleExtractor(String url) {
        this.url = url;
    }

    private void download() throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection
                .setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader r = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
        );

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
        }

        html = sb.toString();
    }

    public String parse() throws IOException {
        String result = "";

        download();

        String input;
        try {
            input = tryReadability();
        } catch (Exception exception) {
            input = html;
        }

        if (StringUtils.hasText(input))
            result = tryBoilerpipe(input);

        if (!StringUtils.hasText(result))
            result = tryBoilerpipe(html);

        return result;
    }

    private String tryReadability() {
        Readability readability = new Readability(html);
        boolean result = readability.init();
        if (result) {
            readability.makeUrlsAbsolute();
            return readability.content();
        } else
            return html;
    }

    private String tryBoilerpipe(String input) {
        if (StringUtils.hasText(input)) {
            try {
                return ArticleExtractor.INSTANCE.getText(input);
            } catch (BoilerpipeProcessingException ex) {
                // nothing
            }
        }

        return null;
    }
}
