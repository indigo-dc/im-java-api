package es.upv.i3m.grycap.im.auth.credential.im;

import es.upv.i3m.grycap.im.auth.credential.AbstractTokenCredential;
import es.upv.i3m.grycap.im.auth.credential.AbstractUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceType;

public final class ImCredential {

  public static class ImTokenCredential
      extends AbstractTokenCredential<ImTokenCredential> {

    protected ImTokenCredential(ImTokenCredentialBuilder builder) {
      super(builder);
    }

    @Override
    public ServiceType getServiceType() {
      return ServiceType.INFRASTRUCTURE_MANAGER;
    }

    public static ImTokenCredentialBuilder getBuilder() {
      return new ImTokenCredentialBuilder();
    }

    public static class ImTokenCredentialBuilder extends
        AbstractTokenCredentialBuilder<ImTokenCredentialBuilder, ImTokenCredential> {

      @Override
      public ImTokenCredential build() {
        return new ImTokenCredential(this);
      }

    }
  }

  public static class ImUsernamePasswordCredential
      extends AbstractUsernamePasswordCredential<ImUsernamePasswordCredential> {
    @Override
    public ServiceType getServiceType() {
      return ServiceType.INFRASTRUCTURE_MANAGER;
    }

    protected ImUsernamePasswordCredential(
        ImUsernamePasswordCredentialBuilder builder) {
      super(builder);
    }

    public static ImUsernamePasswordCredentialBuilder getBuilder() {
      return new ImUsernamePasswordCredentialBuilder();
    }

    //@formatter:off
    public static class ImUsernamePasswordCredentialBuilder extends
        AbstractUsernamePasswordCredentialBuilder<ImUsernamePasswordCredentialBuilder, 
        ImUsernamePasswordCredential> {
      //@formatter:on

      @Override
      public ImUsernamePasswordCredential build() {
        return new ImUsernamePasswordCredential(this);
      }
    }
  }
}
