package es.upv.i3m.grycap.im.pojo.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.upv.i3m.grycap.im.pojo.Property;

import java.io.IOException;
import java.util.Map.Entry;

public class PropertyDeserializer extends JsonDeserializer<Property> {

  @Override
  public Property deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {

    ObjectNode objectNode = new ObjectMapper().readTree(jp);
    Entry<String, JsonNode> field = objectNode.fields().next();

    return new Property(field.getKey(), field.getValue().asText());
  }
}