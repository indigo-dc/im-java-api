package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.upv.i3m.grycap.im.pojo.deserializer.StateDeserializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

@JsonDeserialize(using = StateDeserializer.class)
public class InfrastructureState {

  private final String state;
  private final Map<String, String> vmStates;

  public InfrastructureState(String state, Map<String, String> vmStates) {
    this.state = state;
    this.vmStates = vmStates;
  }

  public String getState() {
    return state;
  }

  public Map<String, String> getVmStates() {
    return vmStates;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(state).append(vmStates).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof InfrastructureState) {
      InfrastructureState infState = (InfrastructureState) other;
      return new EqualsBuilder().append(state, infState.state)
          .append(vmStates, infState.vmStates).isEquals();
    }
    return false;
  }

}