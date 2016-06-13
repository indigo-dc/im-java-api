package es.upv.i3m.grycap.im.auth.credential.gce;

import es.upv.i3m.grycap.im.auth.credential.AbstractUsernamePasswordCredential;
import es.upv.i3m.grycap.im.auth.credential.ServiceProvider;

public class GceUserPwdCredential
    extends AbstractUsernamePasswordCredential<GceUserPwdCredential> {

  private String project;

  protected GceUserPwdCredential(GceUserPwdCredentialBuilder builder) {
    super(builder);
    setProject(builder.getProject());
  }

  public String getProject() {
    return project;
  }

  private void setProject(String project) {
    if (isNullOrEmpty(project)) {
      throw new IllegalArgumentException("Project must not be blank");
    }
    this.project = project;
  }

  @Override
  public ServiceProvider getServiceProvider() {
    return ServiceProvider.GCE;
  }

  @Override
  public StringBuilder serialize(StringBuilder sb) {
    sb = super.serialize(sb);
    sb.append(" ; project = ").append(project);
    return sb;
  }

  public static GceUserPwdCredentialBuilder getBuilder() {
    return new GceUserPwdCredentialBuilder();
  }

  //@formatter:off
  public static class GceUserPwdCredentialBuilder extends
      AbstractUsernamePasswordCredentialBuilder<GceUserPwdCredentialBuilder, 
      GceUserPwdCredential> {
    //@formatter:on

    private String project;

    public String getProject() {
      return project;
    }

    public GceUserPwdCredentialBuilder withProject(String project) {
      this.project = project;
      return this;
    }

    @Override
    public GceUserPwdCredential build() {
      return new GceUserPwdCredential(this);
    }
  }
}