package es.upv.i3m.grycap.im.auth.credential;

public interface Credential<T extends Credential<T>> {

  public ServiceType getServiceType();

  public String serialize();

  public interface CredentialBuilder<T> {

    public T build();

  }
}
