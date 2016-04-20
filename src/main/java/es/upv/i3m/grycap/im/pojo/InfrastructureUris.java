package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfrastructureUris {

  private final List<InfrastructureUri> uris;

  public InfrastructureUris(@JsonProperty("uri-list")
  List<InfrastructureUri> uris) {
    this.uris = uris;
  }

  public List<InfrastructureUri> getUris() {
    return uris;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(uris).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof InfrastructureUris) {
      InfrastructureUris infUris = (InfrastructureUris) other;
      return new EqualsBuilder().append(uris, infUris.uris).isEquals();
    }
    return false;
  }
}