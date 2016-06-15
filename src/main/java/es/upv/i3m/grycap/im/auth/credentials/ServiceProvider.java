package es.upv.i3m.grycap.im.auth.credentials;

public enum ServiceProvider {

  //@formatter:off
  INFRASTRUCTURE_MANAGER("im","InfrastructureManager"),
  VMRC("vmrc","VMRC"),
  DUMMY("dummy","Dummy"),
  OPENNEBULA("one","OpenNebula"),
  EC2("ec2","EC2"),
  FOG_BOW("fog","FogBow"),
  OPENSTACK("ost","OpenStack"),
  OCCI("occi","OCCI"),
  DOCKER("docker","Docker"),
  GCE("gce","GCE"),
  AZURE("azure","Azure"),
  KUBERNETES("kub","Kubernetes");
  //@formatter:on

  private final String id;
  private final String type;

  private ServiceProvider(final String id, final String type) {
    this.id = id;
    this.type = type;
  }

  public final String getId() {
    return id;
  }

  public final String getType() {
    return type;
  }

}
