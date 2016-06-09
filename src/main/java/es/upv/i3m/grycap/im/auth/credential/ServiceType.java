package es.upv.i3m.grycap.im.auth.credential;

public enum ServiceType {

  //@formatter:off
  INFRASTRUCTURE_MANAGER("InfrastructureManager"),
  VMRC("VMRC"),
  DUMMY("Dummy"),
  OPENNEBULA("OpenNebula"),
  EC2("EC2"),
  FOG_BOW("FogBow"),
  OPENSTACK("OpenStack"),
  OCCI("OCCI"),
  LIB_CLOUD("LibCloud"),
  DOCKER("Docker"),
  GCE("GCE"),
  AZURE("Azure"),
  KUBERNETES("Kubernetes"),
  LIB_VIRT("LibVirt");
  //@formatter:on

  private final String value;

  ServiceType(String value) {
    this.value = value;
  }

  public final String getValue() {
    return value;
  }

}
