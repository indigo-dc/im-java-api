package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import es.upv.i3m.grycap.im.exceptions.InfrastructureUuidNotFoundException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfrastructureUri {

  private final String uri;
  private String infrastructureId;
  private static final String UUID_PATTERN =
      "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

  public InfrastructureUri(@JsonProperty("uri") String uri) {
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }

  /**
   * Returns the infrastructure Id extracted from the infrastructure uri.
   */
  public String getInfrastructureId()
      throws InfrastructureUuidNotFoundException {
    if (infrastructureId == null) {
      infrastructureId = extractInfrastructureId();
    }
    return infrastructureId;
  }

  private String extractInfrastructureId()
      throws InfrastructureUuidNotFoundException {
    Pattern ptrn = Pattern.compile(UUID_PATTERN);
    Matcher matcher = ptrn.matcher(uri);
    if (matcher.find()) {
      return matcher.group(0);
    }
    throw new InfrastructureUuidNotFoundException();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(uri).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof InfrastructureUri) {
      InfrastructureUri infUri = (InfrastructureUri) other;
      return new EqualsBuilder().append(uri, infUri.uri).isEquals();
    }
    return false;
  }
}