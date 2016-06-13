package es.upv.i3m.grycap.im.auth.credential;

public abstract class AbstractCredential<T extends AbstractCredential<T>>
    implements Credential<T> {

  protected <B extends AbstractCredentialBuilder<B, T>> AbstractCredential(
      B builder) {
  }

  @Override
  public String serialize() {
    return serialize(null).toString();
  }

  protected StringBuilder serialize(final StringBuilder sb) {
    StringBuilder serializedSb = sb;
    if (serializedSb == null) {
      serializedSb = new StringBuilder();
    }
    serializedSb.append("id = ").append(getServiceProvider().getId())
        .append(" ; ");
    serializedSb.append("type = ").append(getServiceProvider().getType());
    return serializedSb;
  }

  public static boolean isNullOrEmpty(String string) {
    return (string == null) || string.isEmpty();
  }

  //@formatter:off
  public abstract static class AbstractCredentialBuilder
      <B extends AbstractCredentialBuilder<B, T>, T extends AbstractCredential<T>>
      implements CredentialBuilder<T> {
    //@formatter:on

  }

}
