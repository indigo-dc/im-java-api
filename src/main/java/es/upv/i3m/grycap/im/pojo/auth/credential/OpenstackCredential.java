package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public class OpenstackCredential extends AbstractUsernamePasswordCredential<OpenstackCredential> {

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

  public enum AUTH_VERSION {

    PASSWORD_2_0("2.0_password"), PASSWORD_3_x("3.X_password");

    private final String value;

    AUTH_VERSION(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  private AUTH_VERSION authVersion = AUTH_VERSION.PASSWORD_2_0;
  private String tenant;
  private String host;
  private String baseUrl;
  private String serviceRegion;
  private String serviceName;
  private String authToken;

  @Override
  public SERVICE_TYPE getServiceType() {
    return SERVICE_TYPE.OPENSTACK;
  }

  public AUTH_VERSION getAuthVersion() {
    return authVersion;
  }

  public void setAuthVersion(AUTH_VERSION authVersion) {
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

  public void setHost(String host) {
    if (Strings.isNullOrEmpty(host)) {
      throw new IllegalArgumentException("host must not be blank");
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
    if (authVersion != AUTH_VERSION.PASSWORD_2_0) {
      sb.append(" ; auth_version = ").append(authVersion.getValue());
    }
    if (!Strings.isNullOrEmpty(baseUrl)) {
      sb.append(" ; base_url = ").append(baseUrl);
    }
    if (!Strings.isNullOrEmpty(serviceRegion)) {
      sb.append(" ; service_region = ").append(serviceRegion);
    }
    if (!Strings.isNullOrEmpty(serviceName)) {
      sb.append(" ; service_name = ").append(serviceName);
    }
    if (!Strings.isNullOrEmpty(authToken)) {
      sb.append(" ; auth_token = ").append(authToken);
    }
    return sb;
  }

  public static OpenstackCredentialBuilder getBuilder() {
    return new OpenstackCredentialBuilder();
  }

  public static class OpenstackCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<OpenstackCredentialBuilder, OpenstackCredential> {

    private AUTH_VERSION authVersion;
    private String tenant;
    private String host;
    private String baseUrl;
    private String serviceRegion;
    private String serviceName;
    private String authToken;

    public AUTH_VERSION getAuthVersion() {
      return authVersion;
    }

    public OpenstackCredentialBuilder withAuthVersion(AUTH_VERSION authVersion) {
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
