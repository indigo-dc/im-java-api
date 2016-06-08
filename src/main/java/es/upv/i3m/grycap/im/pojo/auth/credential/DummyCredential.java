package es.upv.i3m.grycap.im.pojo.auth.credential;

public class DummyCredential extends AbstractCredential<DummyCredential> {

  protected DummyCredential(DummyCredentialBuilder builder) {
    super(builder);
  }

  @Override
  public SERVICE_TYPE getServiceType() {
    return SERVICE_TYPE.DUMMY;
  }

  public static DummyCredentialBuilder getBuilder() {
    return new DummyCredentialBuilder();
  }

  public static class DummyCredentialBuilder
      extends AbstractCredentialBuilder<DummyCredentialBuilder, DummyCredential> {

    @Override
    public DummyCredential build() {
      return new DummyCredential(this);
    }

  }

}
