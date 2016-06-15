package es.upv.i3m.grycap.im.auth.credentials;

public interface Credentials {

  public String serialize();

  public default boolean isNullOrEmpty(String string) {
    return (string == null) || string.isEmpty();
  }
}
