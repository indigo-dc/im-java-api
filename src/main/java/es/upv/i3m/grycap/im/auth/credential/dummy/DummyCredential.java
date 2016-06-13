package es.upv.i3m.grycap.im.auth.credential.dummy;

import es.upv.i3m.grycap.im.auth.credential.AbstractCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class DummyCredential extends AbstractCredential<DummyCredential> {

  protected DummyCredential(DummyCredentialBuilder builder) {
    super(builder);
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.DUMMY;
  }

  public static DummyCredentialBuilder getBuilder() {
    return new DummyCredentialBuilder();
  }

  public static class DummyCredentialBuilder extends
      AbstractCredentialBuilder<DummyCredentialBuilder, DummyCredential> {

    @Override
    public DummyCredential build() {
      return new DummyCredential(this);
    }

  }

}
