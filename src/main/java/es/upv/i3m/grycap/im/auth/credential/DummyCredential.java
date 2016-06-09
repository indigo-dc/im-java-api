package es.upv.i3m.grycap.im.auth.credential;

public class DummyCredential extends AbstractCredential<DummyCredential> {

  protected DummyCredential(DummyCredentialBuilder builder) {
    super(builder);
  }

  @Override
  public ServiceType getServiceType() {
    return ServiceType.DUMMY;
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
