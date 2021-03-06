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

package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class VmrcCredentials extends GenericCredentials<VmrcCredentials> {

  private VmrcCredentials() {
    super(ServiceProvider.VMRC);
  }

  private VmrcCredentials(String id) {
    super(ServiceProvider.VMRC, id);
  }

  public static VmrcCredentials buildCredentials() {
    return new VmrcCredentials();
  }

  public static VmrcCredentials buildCredentials(String id) {
    return new VmrcCredentials(id);
  }

}
