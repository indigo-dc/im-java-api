package es.upv.i3m.grycap.im.auth.credential.opennebula;

import es.upv.i3m.grycap.im.auth.credential.AbstractTokenCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceType;

public class OpennebulaTokenCredential
    extends AbstractTokenCredential<OpennebulaTokenCredential> {

  private String host;

  protected OpennebulaTokenCredential(
      OpennebulaTokenCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
  }

  public String getHost() {
    return host;
  }

  private void setHost(String host) {
    if (isNullOrEmpty(host)) {
      throw new IllegalArgumentException("Host must not be blank");
    }
    this.host = host;
  }

  @Override
  public ServiceType getServiceType() {
    return ServiceType.OPENNEBULA;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    return sb;
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