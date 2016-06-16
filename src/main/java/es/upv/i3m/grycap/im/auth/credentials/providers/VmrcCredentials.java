package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class VmrcCredentials extends GenericCredentials<VmrcCredentials> {

  private VmrcCredentials() {
    super(ServiceProvider.VMRC);
  }

  public static VmrcCredentials buildCredentials() {
    return new VmrcCredentials();
  }

}
