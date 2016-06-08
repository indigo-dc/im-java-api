package es.upv.i3m.grycap.im.pojo.auth.credential;

public final class IMCredential {

  public static class IMTokenCredential extends AbstractTokenCredential<IMTokenCredential> {

    protected IMTokenCredential(IMTokenCredentialBuilder builder) {
      super(builder);
    }

    @Override
    public SERVICE_TYPE getServiceType() {
      return SERVICE_TYPE.INFRASTRUCTURE_MANAGER;
    }

    public static IMTokenCredentialBuilder getBuilder() {
      return new IMTokenCredentialBuilder();
    }

    public static class IMTokenCredentialBuilder
        extends AbstractTokenCredentialBuilder<IMTokenCredentialBuilder, IMTokenCredential> {

      @Override
      public IMTokenCredential build() {
        return new IMTokenCredential(this);
      }

    }
  }

  public static class IMUsernamePasswordCredential
      extends AbstractUsernamePasswordCredential<IMUsernamePasswordCredential> {
    @Override
    public SERVICE_TYPE getServiceType() {
      return SERVICE_TYPE.INFRASTRUCTURE_MANAGER;
    }

    protected IMUsernamePasswordCredential(IMUsernamePasswordCredentialBuilder builder) {
      super(builder);
    }

    public static IMUsernamePasswordCredentialBuilder getBuilder() {
      return new IMUsernamePasswordCredentialBuilder();
    }

    public static class IMUsernamePasswordCredentialBuilder extends
        AbstractUsernamePasswordCredentialBuilder<IMUsernamePasswordCredentialBuilder, IMUsernamePasswordCredential> {

      @Override
      public IMUsernamePasswordCredential build() {
        return new IMUsernamePasswordCredential(this);
      }

    }
  }
}
