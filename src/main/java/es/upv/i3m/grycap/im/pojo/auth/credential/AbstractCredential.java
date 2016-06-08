package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public abstract class AbstractCredential<T extends AbstractCredential<T>> implements Credential<T> {

  protected <B extends AbstractCredentialBuilder<B, T>> AbstractCredential(B builder) {
    id = builder.getId();
  }

  private String id;

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
    if (!Strings.isNullOrEmpty(id)) {
      sb.append("id = ").append(id).append(" ; ");
    }
    sb.append("type = ").append(getServiceType().getValue());
    return sb;
  }

  public static abstract class AbstractCredentialBuilder<B extends AbstractCredentialBuilder<B, T>, T extends AbstractCredential<T>>
      implements CredentialBuilder<T> {

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
}
