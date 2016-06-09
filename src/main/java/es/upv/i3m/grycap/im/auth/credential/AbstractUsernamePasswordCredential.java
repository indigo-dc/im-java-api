package es.upv.i3m.grycap.im.auth.credential;

//@formatter:off
public abstract class AbstractUsernamePasswordCredential<T extends 
    AbstractUsernamePasswordCredential<T>>
    extends AbstractCredential<T> {
  //@formatter:on

  private String username;
  private String password;

  //@formatter:off
  protected <B extends AbstractUsernamePasswordCredentialBuilder<B, T>> 
      AbstractUsernamePasswordCredential(B builder) {
    //@formatter:on

    super(builder);
    username = builder.getUsername();
    password = builder.getPassword();
  }

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
    if (!isNullOrEmpty(username)) {
      sb.append(" ; username = ").append(username);
    }
    if (!isNullOrEmpty(password)) {
      sb.append(" ; password = ").append(password);
    }
    return sb;
  }

  //@formatter:off
  public abstract static class AbstractUsernamePasswordCredentialBuilder
      <B extends AbstractUsernamePasswordCredentialBuilder<B, T>, 
      T extends AbstractUsernamePasswordCredential<T>>
      extends AbstractCredentialBuilder<B, T> {
    //@formatter:on

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
