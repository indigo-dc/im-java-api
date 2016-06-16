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
