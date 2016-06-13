package es.upv.i3m.grycap.im.auth.credential.occi;

import es.upv.i3m.grycap.im.auth.credential.AbstractCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class OcciCredential extends AbstractCredential<OcciCredential> {

  private String host;
  private String proxy;

  protected OcciCredential(OcciCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
    setProxy(builder.getProxy());
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.OCCI;
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

  public String getProxy() {
    return proxy;
  }

  private void setProxy(String proxy) {
    if (isNullOrEmpty(proxy)) {
      throw new IllegalArgumentException("proxy must not be blank");
    }
    this.proxy = proxy;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    sb.append(" ; proxy = ").append(proxy.replaceAll("\r?\n", "\\n"));
    return sb;
  }

  public static OcciCredentialBuilder getBuilder() {
    return new OcciCredentialBuilder();
  }

  public static class OcciCredentialBuilder
      extends AbstractCredentialBuilder<OcciCredentialBuilder, OcciCredential> {

    private String host;
    private String proxy;

    public OcciCredentialBuilder withHost(String host) {
      this.host = host;
      return this;
    }

    public OcciCredentialBuilder withProxy(String proxy) {
      this.proxy = proxy;
      return this;
    }

    public String getHost() {
      return host;
    }

    public String getProxy() {
      return proxy;
    }

    @Override
    public OcciCredential build() {
      return new OcciCredential(this);
    }
  }
}
