package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class FogBowCredentials extends GenericCredentials<FogBowCredentials> {

  private FogBowCredentials() {
    super(ServiceProvider.FOG_BOW);
  }

  public static FogBowCredentials buildCredentials() {
    return new FogBowCredentials();
  }

}
