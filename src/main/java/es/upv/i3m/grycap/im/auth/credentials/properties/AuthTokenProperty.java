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

public class AuthTokenProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "auth_token";
  private static final String ERROR_MESSAGE =
      "Authorization token must not be blank";

  public AuthTokenProperty(Credentials credential, String authToken) {
    super(credential, PROPERTY_NAME, authToken, ERROR_MESSAGE);
  }

}
