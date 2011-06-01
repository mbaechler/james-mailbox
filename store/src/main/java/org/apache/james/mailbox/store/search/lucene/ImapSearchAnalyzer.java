/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.mailbox.store.search.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SentenceTokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.util.Version;

/**
*
* {@link Analyzer} which match substrings. This is needed because of RFC 3501.
* 
* From RFC:
* 
*      In all search keys that use strings, a message matches the key if
*      the string is a substring of the field.  The matching is
*      case-insensitive.
*
*/
public final class ImapSearchAnalyzer extends Analyzer {

    private final int minTokenLength;
    private final int maxTokenLength;
    
    public ImapSearchAnalyzer() {
        this(3, 40);
    }
    public ImapSearchAnalyzer(int minTokenLength, int maxTokenLength) {
        this.minTokenLength = minTokenLength;
        this.maxTokenLength = maxTokenLength;
    }
   /*
    * (non-Javadoc)
    * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String, java.io.Reader)
    */
   public TokenStream tokenStream(String fieldName, Reader reader) {
       return new NGramTokenFilter(new LowerCaseFilter(Version.LUCENE_31, new SentenceTokenizer(reader)), minTokenLength, maxTokenLength);
   }
   
}