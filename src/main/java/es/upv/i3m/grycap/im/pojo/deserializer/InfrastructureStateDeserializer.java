package es.upv.i3m.grycap.im.pojo.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.upv.i3m.grycap.im.pojo.InfrastructureState;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class InfrastructureStateDeserializer extends JsonDeserializer<InfrastructureState> {

  private static final String INF_STATE_NODE_ID = "state";
  private static final String VM_STATE_NODES_ID = "vm_states";

  @Override
  public InfrastructureState deserialize(JsonParser jp,
      DeserializationContext ctxt) throws IOException {

    // main 'state' node (wrapper created by the im)
    ObjectNode mainNode = new ObjectMapper().readTree(jp);
    Entry<String, JsonNode> mainState = mainNode.fields().next();

    // infrastructure info
    JsonNode stateNode = mainState.getValue();
    // infrastructure state value
    String infrastructureState = stateNode.get(INF_STATE_NODE_ID).asText();

    // vms state value
    JsonNode vmStatesNode = stateNode.get(VM_STATE_NODES_ID);
    Map<String, String> vmStates = new HashMap<>();
    Iterator<Entry<String, JsonNode>> vmStatesIterator = vmStatesNode.fields();
    while (vmStatesIterator.hasNext()) {
      Entry<String, JsonNode> vmState = vmStatesIterator.next();
      vmStates.put(vmState.getKey(), vmState.getValue().asText());
    }

    return new InfrastructureState(infrastructureState, vmStates);
  }
}