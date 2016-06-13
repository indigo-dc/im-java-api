package es.upv.i3m.grycap.im.auth.credential.opennebula;

import es.upv.i3m.grycap.im.auth.credential.AbstractUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class OpenNebulaUserPwdCredential
    extends AbstractUsernamePasswordCredential<OpenNebulaUserPwdCredential> {

  private String host;

  protected OpenNebulaUserPwdCredential(
      OpennebulaUserPwdCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
  }

  public String getHost() {
    return host;
  }

  private void setHost(String host) {
    if (isNullOrEmpty(host)) {
      throw new IllegalArgumentException("host must not be blank");
    }
    this.host = host;
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.OPENNEBULA;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    return sb;
  }

  public static OpennebulaUserPwdCredentialBuilder getBuilder() {
    return new OpennebulaUserPwdCredentialBuilder();
  }

  //@formatter:off
  public static class OpennebulaUserPwdCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<OpennebulaUserPwdCredentialBuilder, 
      OpenNebulaUserPwdCredential> {
    //@formatter:on

    private String host;

    public String getHost() {
      return host;
    }

    public OpennebulaUserPwdCredentialBuilder withHost(String host) {
      this.host = host;
      return this;
    }

    @Override
    public OpenNebulaUserPwdCredential build() {
      return new OpenNebulaUserPwdCredential(this);
    }
  }
}