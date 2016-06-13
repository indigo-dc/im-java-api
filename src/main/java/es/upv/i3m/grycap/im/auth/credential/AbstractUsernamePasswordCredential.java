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

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  @Override
  public StringBuilder serialize(final StringBuilder sb) {
    StringBuilder serializedSb = sb;
    serializedSb = super.serialize(serializedSb);
    if (!isNullOrEmpty(username)) {
      serializedSb.append(" ; username = ").append(username);
    }
    if (!isNullOrEmpty(password)) {
      serializedSb.append(" ; password = ").append(password);
    }
    return serializedSb;
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
    public B withUsername(final String username) {
      this.username = username;
      return (B) this;
    }

    @SuppressWarnings("unchecked")
    public B withPassword(final String password) {
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
