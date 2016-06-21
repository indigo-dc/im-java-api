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

package es.upv.i3m.grycap.im.auth.credentials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AuthorizationHeader {

  private static final String ERROR_MESSAGE = "Credentials must not be null";
  private List<Credentials> credentials = new ArrayList<>();

  public List<Credentials> getCredentials() {
    return credentials;
  }

  /**
   * Sets the credentials information.
   */
  public void setCredentialsAuthInfos(List<Credentials> credentials) {
    if (credentials == null) {
      throw new IllegalArgumentException(ERROR_MESSAGE);
    }
    this.credentials = credentials;
  }

  public void addCredential(Credentials credential) {
    credentials.add(credential);
  }

  /**
   * Returns a string with the credentials information.
   */
  public String serialize() {
    StringBuilder sb = new StringBuilder();
    Iterator<Credentials> it = credentials.iterator();
    while (it.hasNext()) {
      String serializedAuthInfo = it.next().serialize();
      sb.append(serializedAuthInfo);
      if (it.hasNext()) {
        sb.append("\\n");
      }
    }
    return sb.toString();
  }

}
