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
import es.upv.i3m.grycap.im.auth.credentials.providers.OpenstackAuthVersion;

public class OpenStackAuthVersionProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "auth_version";
  private static final String ERROR_MESSAGE =
      "OpenStack auth version must not be blank";

  public OpenStackAuthVersionProperty(Credentials credential,
      OpenstackAuthVersion openstackAuthVersion) {
    super(credential, PROPERTY_NAME, openstackAuthVersion.getValue(),
        ERROR_MESSAGE);
  }

  @Override
  public String serialize() {
    // Default property, not needed to add it to the credentials
    if (!OpenstackAuthVersion.PASSWORD_2_0.compare(getPropertyValue())) {
      return super.serialize();
    }
    return getCredentials().serialize();
  }

}
