package es.upv.i3m.grycap.im.auth.credential;

public abstract class AbstractCredential<T extends AbstractCredential<T>>
    implements Credential<T> {

  private String id;

  protected <B extends AbstractCredentialBuilder<B, T>> AbstractCredential(
      B builder) {
    id = builder.getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String serialize() {
    return serialize(null).toString();
  }

  protected StringBuilder serialize(StringBuilder sb) {
    if (sb == null) {
      sb = new StringBuilder();
    }
    if (!isNullOrEmpty(id)) {
      sb.append("id = ").append(id).append(" ; ");
    }
    sb.append("type = ").append(getServiceType().getValue());
    return sb;
  }

  //@formatter:off
  public abstract static class AbstractCredentialBuilder
      <B extends AbstractCredentialBuilder<B, T>, T extends AbstractCredential<T>>
      implements CredentialBuilder<T> {
    //@formatter:on

    private String id;

    public String getId() {
      return id;
    }

    @SuppressWarnings("unchecked")
    public B withId(String id) {
      this.id = id;
      return (B) this;
    }
  }

  public static boolean isNullOrEmpty(String string) {
    return (string == null) || string.isEmpty();
  }
}
