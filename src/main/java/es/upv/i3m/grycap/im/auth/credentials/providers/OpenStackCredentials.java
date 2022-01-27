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
import es.upv.i3m.grycap.im.auth.credentials.properties.AuthTokenProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.BaseUrlProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.DomainProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ImageUrlProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.MicroversionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.NetworkUrlPropertyç;
import es.upv.i3m.grycap.im.auth.credentials.properties.OpenStackAuthVersionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ServiceNameProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ServiceRegionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.TenantProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.VolumeUrlProperty;

public class OpenStackCredentials
    extends GenericCredentials<OpenStackCredentials> {

  private OpenStackCredentials() {
    super(ServiceProvider.OPENSTACK);
  }
  
  private OpenStackCredentials(String id) {
    super(ServiceProvider.OPENSTACK, id);
  }

  public static OpenStackCredentials buildCredentials() {
    return new OpenStackCredentials();
  }
  
  public static OpenStackCredentials buildCredentials(String id) {
    return new OpenStackCredentials(id);
  }

  public OpenStackCredentials withTenant(String tenant) {
    setCredentials(new TenantProperty(getCredentials(), tenant));
    return this;
  }

  public OpenStackCredentials withBaseUrl(String baseUrl) {
    setCredentials(new BaseUrlProperty(getCredentials(), baseUrl));
    return this;
  }

  public OpenStackCredentials withServiceRegion(String serviceRegion) {
    setCredentials(new ServiceRegionProperty(getCredentials(), serviceRegion));
    return this;
  }

  public OpenStackCredentials withServiceName(String serviceName) {
    setCredentials(new ServiceNameProperty(getCredentials(), serviceName));
    return this;
  }

  public OpenStackCredentials withAuthToken(String authToken) {
    setCredentials(new AuthTokenProperty(getCredentials(), authToken));
    return this;
  }

  public OpenStackCredentials withDomain(String domain) {
    setCredentials(new DomainProperty(getCredentials(), domain));
    return this;
  }

  public OpenStackCredentials withMicroversion(String microversion) {
    setCredentials(new MicroversionProperty(getCredentials(), microversion));
    return this;
  }

  public OpenStackCredentials withImageUrl(String image_url) {
    setCredentials(new ImageUrlProperty(getCredentials(), image_url));
    return this;
  }

  public OpenStackCredentials withNetworkUrl(String network_url) {
    setCredentials(new NetworkUrlProperty(getCredentials(), network_url));
    return this;
  }

  public OpenStackCredentials withVolumeUrl(String volume_url) {
    setCredentials(new VolumeUrlProperty(getCredentials(), volume_url));
    return this;
  }

  /**
   * Sets the authorization version specific for OpenStack.
   */
  public OpenStackCredentials
      withAuthVersion(OpenStackAuthVersion authVersion) {
    setCredentials(
        new OpenStackAuthVersionProperty(getCredentials(), authVersion));
    return this;
  }
}
