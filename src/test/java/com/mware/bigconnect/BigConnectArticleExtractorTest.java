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

import org.junit.Test;

public class BigConnectArticleExtractorTest {
    @Test
    public void testExtractor() throws Exception {
        BigConnectArticleExtractor extractor = new BigConnectArticleExtractor("https://www.mediafax.ro/stirile-zilei/in-cautarea-revolutiei-pierdute-cine-l-a-tradat-pe-dictator-ep-1-19778672");
        String parse = extractor.parse();
        System.out.println(parse);
    }
}
