package es.upv.i3m.grycap.im.auth.credential.openstack;

public enum OpenstackAuthVersion {

  //@formatter:off
  PASSWORD_2_0("2.0_password"), 
  PASSWORD_3_X("3.X_password");
  //@formatter:on

  private final String value;

  OpenstackAuthVersion(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
