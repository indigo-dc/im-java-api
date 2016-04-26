package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import es.upv.i3m.grycap.im.States;
import es.upv.i3m.grycap.im.exceptions.NoEnumFoundException;
import es.upv.i3m.grycap.im.pojo.deserializer.InfrastructureStateDeserializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;
import java.util.Map.Entry;

@JsonDeserialize(using = InfrastructureStateDeserializer.class)
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

  public States getEnumState() throws NoEnumFoundException {
    return States.getEnumFromValue(state);
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

  /**
   * Returns a formatted string with the information of the infrastructure
   * state.<br>
   * Example return: <br>
   * Infrastructure state 'running'<br>
   * - Virtual machine '0' state 'running'<br>
   * - Virtual machine '1' state 'running'
   */
  public String getFormattedInfrastructureStateString() {
    String result = "Infrastructure state '" + getState() + "'\n";
    for (Entry<String, String> vmState : vmStates.entrySet()) {
      result += " - Virtual machine '" + vmState.getKey() + "' state ' "
          + vmState.getValue() + "'";
    }
    return result;
  }
}