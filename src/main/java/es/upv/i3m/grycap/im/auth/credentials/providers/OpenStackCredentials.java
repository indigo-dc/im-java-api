package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;
import es.upv.i3m.grycap.im.auth.credentials.properties.AuthTokenProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.BaseUrlProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.OpenStackAuthVersionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ServiceNameProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.ServiceRegionProperty;
import es.upv.i3m.grycap.im.auth.credentials.properties.TenantProperty;

public class OpenStackCredentials
    extends GenericCredentials<OpenStackCredentials> {

  private OpenStackCredentials() {
    super(ServiceProvider.OPENSTACK);
  }

  public static OpenStackCredentials buildCredentials() {
    return new OpenStackCredentials();
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

  /**
   * Sets the authorization version specific for OpenStack.
   */
  public OpenStackCredentials
      withAuthVersion(OpenstackAuthVersion authVersion) {
    setCredentials(
        new OpenStackAuthVersionProperty(getCredentials(), authVersion));
    return this;
  }
}
