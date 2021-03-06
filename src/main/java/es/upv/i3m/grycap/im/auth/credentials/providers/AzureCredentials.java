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
import es.upv.i3m.grycap.im.auth.credentials.properties.PrivateKeyProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.PublicKeyProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.SubscriptionIdProperty;

public class AzureCredentials extends GenericCredentials<AzureCredentials> {

  private AzureCredentials() {
    super(ServiceProvider.AZURE);
  }

  private AzureCredentials(String id) {
    super(ServiceProvider.AZURE, id);
  }

  public static AzureCredentials buildCredentials() {
    return new AzureCredentials();
  }

  public static AzureCredentials buildCredentials(String id) {
    return new AzureCredentials(id);
  }

  public AzureCredentials withPublicKey(String publicKey) {
    setCredentials(new PrivateKeyProperty(getCredentials(), publicKey));
    return this;
  }

  public AzureCredentials withPrivateKey(String privateKey) {
    setCredentials(new PublicKeyProperty(getCredentials(), privateKey));
    return this;
  }
  
  public AzureCredentials withSubscriptionId(String subscriptionId) {
    setCredentials(new SubscriptionIdProperty(getCredentials(), subscriptionId));
    return this;
  }

}
