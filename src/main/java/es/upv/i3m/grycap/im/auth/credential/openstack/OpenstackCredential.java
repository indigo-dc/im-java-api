package es.upv.i3m.grycap.im.auth.credential.openstack;

import es.upv.i3m.grycap.im.auth.credential.AbstractUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class OpenstackCredential
    extends AbstractUsernamePasswordCredential<OpenstackCredential> {

  private OpenstackAuthVersion authVersion = OpenstackAuthVersion.PASSWORD_2_0;
  private String tenant;
  private String host;
  private String baseUrl;
  private String serviceRegion;
  private String serviceName;
  private String authToken;

  protected OpenstackCredential(OpenstackCredentialBuilder builder) {
    super(builder);
    authVersion = builder.getAuthVersion();
    tenant = builder.getTenant();
    setHost(builder.getHost());
    baseUrl = builder.getBaseUrl();
    serviceRegion = builder.getServiceRegion();
    serviceName = builder.getServiceName();
    authToken = builder.getAuthToken();
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.OPENSTACK;
  }

  public OpenstackAuthVersion getAuthVersion() {
    return authVersion;
  }

  public void setAuthVersion(OpenstackAuthVersion authVersion) {
    this.authVersion = authVersion;
  }

  public String getTenant() {
    return tenant;
  }

  public void setTenant(String tenant) {
    this.tenant = tenant;
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

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getServiceRegion() {
    return serviceRegion;
  }

  public void setServiceRegion(String serviceRegion) {
    this.serviceRegion = serviceRegion;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; host = ").append(host);
    if (authVersion != OpenstackAuthVersion.PASSWORD_2_0) {
      sb.append(" ; OpenstackAuthVersion = ").append(authVersion.getValue());
    }
    if (!isNullOrEmpty(baseUrl)) {
      sb.append(" ; base_url = ").append(baseUrl);
    }
    if (!isNullOrEmpty(serviceRegion)) {
      sb.append(" ; service_region = ").append(serviceRegion);
    }
    if (!isNullOrEmpty(serviceName)) {
      sb.append(" ; service_name = ").append(serviceName);
    }
    if (!isNullOrEmpty(authToken)) {
      sb.append(" ; auth_token = ").append(authToken);
    }
    return sb;
  }

  public static OpenstackCredentialBuilder getBuilder() {
    return new OpenstackCredentialBuilder();
  }

  public static class OpenstackCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<OpenstackCredentialBuilder, OpenstackCredential> {

    private OpenstackAuthVersion authVersion;
    private String tenant;
    private String host;
    private String baseUrl;
    private String serviceRegion;
    private String serviceName;
    private String authToken;

    public OpenstackAuthVersion getAuthVersion() {
      return authVersion;
    }

    public OpenstackCredentialBuilder
        withAuthVersion(OpenstackAuthVersion authVersion) {
      this.authVersion = authVersion;
      return this;
    }

    public String getTenant() {
      return tenant;
    }

    public OpenstackCredentialBuilder withTenant(String tenant) {
      this.tenant = tenant;
      return this;
    }

    public String getHost() {
      return host;
    }

    public OpenstackCredentialBuilder withHost(String host) {
      this.host = host;
      return this;
    }

    public String getBaseUrl() {
      return baseUrl;
    }

    public OpenstackCredentialBuilder withBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    public String getServiceRegion() {
      return serviceRegion;
    }

    public OpenstackCredentialBuilder withServiceRegion(String serviceRegion) {
      this.serviceRegion = serviceRegion;
      return this;
    }

    public String getServiceName() {
      return serviceName;
    }

    public OpenstackCredentialBuilder withServiceName(String serviceName) {
      this.serviceName = serviceName;
      return this;
    }

    public String getAuthToken() {
      return authToken;
    }

    public OpenstackCredentialBuilder withAuthToken(String authToken) {
      this.authToken = authToken;
      return this;
    }

    @Override
    public OpenstackCredential build() {
      return new OpenstackCredential(this);
    }
  }
}
