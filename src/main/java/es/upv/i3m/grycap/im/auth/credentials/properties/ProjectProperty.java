package es.upv.i3m.grycap.im.auth.credentials.properties;

import es.upv.i3m.grycap.im.auth.credentials.Credentials;

public class ProjectProperty extends GenericProperty {

  private static final String PROPERTY_NAME = "project";
  private static final String ERROR_MESSAGE = "Project must not be blank";

  public ProjectProperty(Credentials credential, String project) {
    super(credential, PROPERTY_NAME, project, ERROR_MESSAGE);
  }

}
