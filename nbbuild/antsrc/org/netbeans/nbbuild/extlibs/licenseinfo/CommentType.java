/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.nbbuild.extlibs.licenseinfo;

public enum CommentType {
    TEMPLATE_MINIMAL_IP("The file is used as a template and contains minimal IP."),
    CATEGORY_B("The contained code is used at runtime and must be included in source form."),
    COMMENT_UNSUPPORTED("Filetype does not support comments."),
    GUI_USABILITY("Code is visible at runtime and added license would negatively affect usability."),
    TEST_DATA("The file is used in tests and license header can't be added.");
    
    private String outputComment;
    
    CommentType(String outputComment) {
        this.outputComment = outputComment;
    }
    
    public String getOutputComment() {
        return outputComment;
    }
}
