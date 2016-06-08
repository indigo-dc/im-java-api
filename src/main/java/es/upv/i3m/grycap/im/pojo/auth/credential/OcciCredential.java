package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public class OcciCredential extends AbstractCredential<OcciCredential> {

  protected OcciCredential(OcciCredentialBuilder builder) {
    super(builder);
    setHost(builder.getHost());
    setProxy(builder.getProxy());
  }

  @Override
  public SERVICE_TYPE getServiceType() {
    return SERVICE_TYPE.OCCI;
  }

  private String host;
  private String proxy;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    if (Strings.isNullOrEmpty(host)) {
      throw new IllegalArgumentException("host must not be blank");
    }
    this.host = host;
  }

  public String getProxy() {
    return proxy;
  }

  public void setProxy(String proxy) {
    if (Strings.isNullOrEmpty(proxy)) {
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
