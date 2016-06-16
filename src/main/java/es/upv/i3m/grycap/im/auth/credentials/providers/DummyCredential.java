package es.upv.i3m.grycap.im.auth.credentials.providers;

import es.upv.i3m.grycap.im.auth.credentials.ServiceProvider;

public class DummyCredential extends GenericCredentials<DummyCredential> {

  private DummyCredential() {
    super(ServiceProvider.DUMMY);
  }

  public static DummyCredential buildCredentials() {
    return new DummyCredential();
  }

}
