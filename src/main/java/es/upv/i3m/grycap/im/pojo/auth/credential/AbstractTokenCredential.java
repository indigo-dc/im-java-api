package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public abstract class AbstractTokenCredential<T extends AbstractTokenCredential<T>>
    extends AbstractCredential<T> {

  protected <B extends AbstractTokenCredentialBuilder<B, T>> AbstractTokenCredential(B builder) {
    super(builder);
    setToken(builder.getToken());
  }

  protected String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    if (Strings.isNullOrEmpty(token)) {
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

  public static abstract class AbstractTokenCredentialBuilder<B extends AbstractTokenCredentialBuilder<B, T>, T extends AbstractTokenCredential<T>>
      extends AbstractCredentialBuilder<B, T> {
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
