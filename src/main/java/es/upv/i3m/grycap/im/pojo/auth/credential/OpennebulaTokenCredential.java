package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public class OpennebulaTokenCredential extends AbstractTokenCredential<OpennebulaTokenCredential> {

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

  protected OpennebulaTokenCredential(OpennebulaTokenCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
  }

  public static OpennebulaTokenCredentialBuilder getBuilder() {
    return new OpennebulaTokenCredentialBuilder();
  }

  public static class OpennebulaTokenCredentialBuilder extends
      AbstractTokenCredentialBuilder<OpennebulaTokenCredentialBuilder, OpennebulaTokenCredential> {

    private String host;

    public String getHost() {
      return host;
    }

    public OpennebulaTokenCredentialBuilder withHost(String host) {
      this.host = host;
      return this;
    }

    @Override
    public OpennebulaTokenCredential build() {
      return new OpennebulaTokenCredential(this);
    }

  }
}