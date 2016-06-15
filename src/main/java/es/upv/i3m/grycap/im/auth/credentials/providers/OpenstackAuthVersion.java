package es.upv.i3m.grycap.im.auth.credentials.providers;

public enum OpenstackAuthVersion {

  //@formatter:off
  PASSWORD_2_0("2.0_password"), 
  PASSWORD_3_X("3.X_password"),
  PASSWORD_3_X_TOKEN("3.x_oidc_access_token");
  //@formatter:on

  private final String value;

  OpenstackAuthVersion(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  /**
   * Compare the passed value with the internal value of the enum.
   * 
   * @return: true if the strings are equal, false otherwise.
   */
  public boolean compare(String value) {
    if (value != null && !value.isEmpty()) {
      return this.value.equals(value);
    }
    return false;
  }
}
