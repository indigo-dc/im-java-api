package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public class OpennebulaUserPwdCredential
    extends AbstractUsernamePasswordCredential<OpennebulaUserPwdCredential> {

  private String host;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    if (Strings.isNullOrEmpty(host)) {
      throw new IllegalArgumentException("host must not be blank");
    }
    this.host = host;
  }

  @Override
  public SERVICE_TYPE getServiceType() {
    return SERVICE_TYPE.OPENNEBULA;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    return sb;
  }

  protected OpennebulaUserPwdCredential(OpennebulaUserPwdCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
  }

  public static OpennebulaUserPwdCredentialBuilder getBuilder() {
    return new OpennebulaUserPwdCredentialBuilder();
  }

  public static class OpennebulaUserPwdCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<OpennebulaUserPwdCredentialBuilder, OpennebulaUserPwdCredential> {

    private String host;

    public String getHost() {
      return host;
    }

    public OpennebulaUserPwdCredentialBuilder withHost(String host) {
      this.host = host;
      return this;
    }

    @Override
    public OpennebulaUserPwdCredential build() {
      return new OpennebulaUserPwdCredential(this);
    }

  }
}