package es.upv.i3m.grycap.im.auth.credential.vmrc;

import es.upv.i3m.grycap.im.auth.credential.AbstractUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class VmrcCredential
    extends AbstractUsernamePasswordCredential<VmrcCredential> {

  private String host;

  protected VmrcCredential(VmrcCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());

  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.VMRC;
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
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    return sb;
  }

  public static VmrcCredentialBuilder getBuilder() {
    return new VmrcCredentialBuilder();
  }

  public static class VmrcCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<VmrcCredentialBuilder, VmrcCredential> {

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
