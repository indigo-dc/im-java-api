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

package es.upv.i3m.grycap.im.rest.client;

import es.upv.i3m.grycap.im.exceptions.NoEnumFoundException;
import es.upv.i3m.grycap.im.lang.ImMessages;

/**
 * Types to be used in the REST calls to specify the body content type.
 */
public enum BodyContentType {

  RADL("text/plain"),
  RADL_JSON("application/json"),
  TOSCA("text/yaml");

  private final String value;

  BodyContentType(String value) {
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
   * Returns a RestApiBodyContentType if the String passed is the same as one of
   * the states of the enum<br>
   * 
   * @param value
   *          : string of the value to retrieve
   * @return A RestApiBodyContentType, null if not found.
   * @throws NoEnumFoundException
   *           : No enumerator found
   */
  public static BodyContentType getEnumFromValue(String value)
      throws NoEnumFoundException {
    if (value != null) {
      for (BodyContentType imValue : BodyContentType.values()) {
        if (value.equalsIgnoreCase(imValue.getValue())) {
          return imValue;
        }
      }
    }
    throw new NoEnumFoundException(
        ImMessages.EXCEPTION_NO_REST_API_CONTENT_TYPE_ENUM_FOUND);
  }
}
