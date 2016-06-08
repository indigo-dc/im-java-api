package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public class VmrcCredential extends AbstractUsernamePasswordCredential<VmrcCredential> {

  protected VmrcCredential(VmrcCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
  }

  @Override
  public SERVICE_TYPE getServiceType() {
    return SERVICE_TYPE.VMRC;
  }

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
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    return sb;
  }

  public static VmrcCredentialBuilder getBuilder() {
    return new VmrcCredentialBuilder();
  }

  public static class VmrcCredentialBuilder
      extends AbstractUsernamePasswordCredentialBuilder<VmrcCredentialBuilder, VmrcCredential> {

    private String host;

    public VmrcCredentialBuilder withHost(String host) {
      this.host = host;
      return this;
    }

    public String getHost() {
      return host;
    }

    @Override
    public VmrcCredential build() {
      return new VmrcCredential(this);
    }

  }
}
