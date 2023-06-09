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

package org.netbeans.beaninfo.editors;

import java.beans.*;
import org.openide.explorer.propertysheet.editors.EnhancedPropertyEditor;
import org.openide.nodes.PropertyEditorRegistration;

/**
 * Editor for Character.TYPE
 * @author  Petr Zajac, David Strupl
 */
@PropertyEditorRegistration(targetType={Character.class, char.class})
public class CharEditor extends PropertyEditorSupport implements EnhancedPropertyEditor {

    /**
     * Converts the char to String by either leaving
     * the single char or by creating unicode escape.
     */
    @Override
    public String getAsText () {
        char value = ((Character)getValue()).charValue();
        final StringBuffer buf = new StringBuffer(6);
        switch (value) {
            case '\b': buf.append("\\b"); break; // NOI18N
            case '\t': buf.append("\\t"); break; // NOI18N
            case '\n': buf.append("\\n"); break; // NOI18N
            case '\f': buf.append("\\f"); break; // NOI18N
            case '\r': buf.append("\\r"); break; // NOI18N
            case '\\': buf.append("\\\\"); break; // NOI18N
            default:
                if (value >= 0x0020 && value <= 0x007f)
                    buf.append(value);
                else {
                    buf.append("\\u"); // NOI18N
                    String hex = Integer.toHexString(value);
                    for (int j = 0; j < 4 - hex.length(); j++)
                        buf.append('0');
                    buf.append(hex);
                }
        }
        return buf.toString() ;
    }
    /**
     * Set the property value by parsing given String.
     * @param text  The string to be parsed.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.length() < 1) {
            // ignore empty value
            return;
        }
        char value = 0;
        if (text.charAt(0) == '\\') {
            // backslash means unicode escape sequence
            char ch = text.length() >=2 ? text.charAt(1) : '\\';
            switch (ch) {
                case 'b': value = '\b'; break;
                case 't': value = '\t'; break;
                case 'n': value = '\n'; break;
                case 'f': value = '\f'; break;
                case 'r': value = '\r'; break;
                case '\\': value = '\\' ; break;
                case 'u' :
                    String num = text.substring(2);
                    if (num.length () > 4) {
                        // ignore longer strings
                        return;
                    }
                    try {
                        int intValue = Integer.parseInt(num,16);
                        value = (char) intValue;
                        break;
                    } catch (NumberFormatException nfe) {
                        // ignore non parsable strings
                        return;
                    }
                default:
                        // ignore non-chars after backslash
                        return;
                        
            }
        } else {
            value = text.charAt(0);
        }
        setValue(Character.valueOf(value));
    }
    
    /**
     * Accepts Character and String values. If the argument is
     * a String the first character is taken as the new value.
     * @param newValue new value
     */
    @Override
    public void setValue(Object newValue) throws IllegalArgumentException {
        if (newValue instanceof Character ) {
            super.setValue(newValue);
            return;
        }
        if (newValue instanceof String) {
            String text = (String ) newValue;
            if (text.length() >= 1) {
                super.setValue(Character.valueOf(text.charAt(0)));
                return;
            }
        }
        if (newValue == null ) {
            super.setValue( Character.valueOf( '\u0000' ) ); // NOI18N
            return;
        }
        
        throw new IllegalArgumentException();
    }
    
    /**
     * This method is intended for use when generating Java code to set
     * the value of the property.  It should return a fragment of Java code
     * that can be used to initialize a variable with the current property
     * value.
     * <p>
     * Example results are "2", "new Color(127,127,34)", "Color.orange", etc.
     *
     * @return A fragment of Java code representing an initializer for the
     *   	current value.
     */
    @Override
    public String getJavaInitializationString() {
        if ( ((Character)getValue()).charValue() == '\'' )
            return "'\\''";                 // NOI18N
        else
            return "'" + getAsText() + "'"; // NOI18N
    }

    /**
     * We don't support in place custom editor.
     * @return custom property editor to be shown inside the property
     * sheet.
     */
    public java.awt.Component getInPlaceCustomEditor () {
        return null;
    }
    
    /**
     * We don't support in place custom editor.
     * @return true if this PropertyEditor provides a enhanced in-place custom
     * property editor, false otherwise
     */
    public boolean hasInPlaceCustomEditor () {
        return false;
    }
    
    /**
     * @return true if this property editor provides tagged values and
     * a custom strings in the choice should be accepted too, false otherwise
     */
    public boolean supportsEditingTaggedValues () {
        return true;
    }
}
