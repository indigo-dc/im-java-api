/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.upv.i3m.grycap.im.api;

/**
 * Enumerates static values related with the Infrastructure.<br>
 */
public enum ImValues {

    START("start"),
    STOP("stop"),
    CONTMSG("contmsg"),
    RADL("radl"),
    STATE("state"),
    RECONFIGURE("reconfigure");

    private final String value;

    ImValues(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return value;
    }

    /**
     * Returns a ImValue if the String passed is the same as one of the states
     * of the enum<br>
     * 
     * @param value
     *            : string of the value to retrieve
     * @return A ImValue, null if not found.
     */
    public static ImValues getEnumFromValue(String value) {
        if (value != null) {
            for (ImValues imValue : ImValues.values()) {
                if (value.equalsIgnoreCase(imValue.getValue())) {
                    return imValue;
                }
            }
        }
        return null;
    }
}
