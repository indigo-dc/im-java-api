package es.upv.i3m.grycap.im.pojo.auth.credential;

import com.google.common.base.Strings;

public abstract class AbstractUsernamePasswordCredential<T extends AbstractUsernamePasswordCredential<T>>
    extends AbstractCredential<T> {

  protected <B extends AbstractUsernamePasswordCredentialBuilder<B, T>> AbstractUsernamePasswordCredential(
      B builder) {
    super(builder);
    username = builder.getUsername();
    password = builder.getPassword();
  }

  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    if (!Strings.isNullOrEmpty(username)) {
      sb.append(" ; username = ").append(username);
    }
    if (!Strings.isNullOrEmpty(password)) {
      sb.append(" ; password = ").append(password);
    }
    return sb;
  }

  public static abstract class AbstractUsernamePasswordCredentialBuilder<B extends AbstractUsernamePasswordCredentialBuilder<B, T>, T extends AbstractUsernamePasswordCredential<T>>
      extends AbstractCredentialBuilder<B, T> {
    private String username;
    private String password;

    @SuppressWarnings("unchecked")
    public B withUsername(String username) {
      this.username = username;
      return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B withPassword(String password) {
      this.password = password;
      return (B) this;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }

  }
}
