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

package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class GenericProperty implements Credentials {

  private final Credentials credential;
  private String propertyName;
  private String propertyValue;
  private final String errorMessage;
  private static final String NULL_CREDENTIALS = "Empty credentials error.";

  /**
   * Generic constructor for all the properties that the authorization header
   * can have.
   */
  public GenericProperty(Credentials credential, String propertyName,
      String propertyValue, String errorMessage) {
    this.credential = credential;
    this.propertyName = propertyName;
    this.propertyValue = propertyValue;
    this.errorMessage = errorMessage;
  }

  protected String getPropertyValue() {
    return propertyValue;
  }

  protected Credentials getCredentials() {
    return credential;
  }

  @Override
  public String serialize() {
    if (credential != null) {
      StringBuilder credentials = new StringBuilder(credential.serialize());
      if (!isNullOrEmpty(propertyValue)) {
        credentials.append(" ; " + propertyName + " = ").append(propertyValue);
      } else {
        throw new IllegalArgumentException(errorMessage);
      }
      return credentials.toString();
    } else {
      throw new NullPointerException(NULL_CREDENTIALS);
    }

  }

}
