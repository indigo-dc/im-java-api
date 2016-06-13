package es.upv.i3m.grycap.im.auth.credential.docker;

import es.upv.i3m.grycap.im.auth.credential.AbstractCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class DockerCredential extends AbstractCredential<DockerCredential> {

  private String host;

  protected DockerCredential(DockerCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
  }

  public String getHost() {
    return host;
  }

  private void setHost(final String host) {
    if (isNullOrEmpty(host)) {
      throw new IllegalArgumentException("Host must not be blank");
    }
    this.host = host;
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.DOCKER;
  }

  @Override
  public StringBuilder serialize(final StringBuilder sb) {
    StringBuilder serializedSb = sb;
    serializedSb = super.serialize(serializedSb);
    serializedSb.append(" ; host = ").append(host);
    return serializedSb;
  }

  public static DockerCredentialBuilder getBuilder() {
    return new DockerCredentialBuilder();
  }

  public static class DockerCredentialBuilder extends
      AbstractCredentialBuilder<DockerCredentialBuilder, DockerCredential> {

    private String host;

    public String getHost() {
      return host;
    }

    public DockerCredentialBuilder withHost(final String host) {
      this.host = host;
      return this;
    }

    @Override
    public DockerCredential build() {
      return new DockerCredential(this);
    }
  }
}