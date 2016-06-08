package es.upv.i3m.grycap.im.pojo.auth.credential;

public interface Credential<T extends Credential<T>> {

  public static enum SERVICE_TYPE {

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

    SERVICE_TYPE(String value) {
      this.value = value;
    }

    public final String getValue() {
      return value;
    }
  }

  public SERVICE_TYPE getServiceType();

  public String serialize();

  public interface CredentialBuilder<T> {

    public T build();

  }
}
