/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.upv.i3m.grycap.im;

import es.upv.i3m.grycap.im.exceptions.NoEnumFoundException;
import es.upv.i3m.grycap.im.lang.ImMessages;

/**
 * Stores the virtual machine states.
 */
public enum States {

  PENDING("pending"),
  RUNNING("running"),
  UNCONFIGURED("unconfigured"),
  CONFIGURED("configured"),
  STOPPED("stopped"),
  OFF("off"),
  FAILED("failed"),
  UNKNOWN("unknown");

  private final String value;

  States(String value) {
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
   * Returns a VmState if the String passed is the same as one of the states of
   * the enumerator.<br>
   * 
   * @param value
   *          : string of the value to retrieve
   * @return A VmProperty
   */
  public static States getEnumFromValue(String value)
      throws NoEnumFoundException {
    if (value != null) {
      for (States property : States.values()) {
        if (value.equalsIgnoreCase(property.getValue())) {
          return property;
        }
      }
    }
    throw new NoEnumFoundException(ImMessages.EXCEPTION_NO_STATE_ENUM_FOUND);
  }

}
