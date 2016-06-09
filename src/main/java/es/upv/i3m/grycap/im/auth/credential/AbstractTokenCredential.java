package es.upv.i3m.grycap.im.auth.credential;

public abstract class AbstractTokenCredential<T extends AbstractTokenCredential<T>>
    extends AbstractCredential<T> {

  protected String token;

  protected <B extends AbstractTokenCredentialBuilder<B, T>> AbstractTokenCredential(
      B builder) {
    super(builder);
    setToken(builder.getToken());
  }

  public String getToken() {
    return token;
  }

  private void setToken(String token) {
    if (isNullOrEmpty(token)) {
      throw new IllegalArgumentException("token must not be blank");
    }
    this.token = token;
  }

  @Override
  protected StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; token = ").append(token);
    return sb;
  }

  //@formatter:off
  public abstract static class AbstractTokenCredentialBuilder
      <B extends AbstractTokenCredentialBuilder<B, T>, T extends AbstractTokenCredential<T>>
      extends AbstractCredentialBuilder<B, T> {
    //@formatter:on
    private String token;

    @SuppressWarnings("unchecked")
    public B withToken(String token) {
      this.token = token;
      return (B) this;
    }

    public String getToken() {
      return token;
    }
  }
}