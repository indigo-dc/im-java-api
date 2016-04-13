package es.upv.i3m.grycap.im.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

  @JsonProperty("message")
  private String message;
  @JsonProperty("code")
  private Integer code;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return
   *         The message
   */
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  /**
   * 
   * @param message
   *          The message
   */
  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

  public Error withMessage(String message) {
    this.message = message;
    return this;
  }

  /**
   * 
   * @return
   *         The code
   */
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  /**
   * 
   * @param code
   *          The code
   */
  @JsonProperty("code")
  public void setCode(Integer code) {
    this.code = code;
  }

  public Error withCode(Integer code) {
    this.code = code;
    return this;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  public Error withAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    return this;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(message).append(code).append(additionalProperties)
        .toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof Error) == false) {
      return false;
    }
    Error rhs = ((Error) other);
    return new EqualsBuilder().append(message, rhs.message).append(code, rhs.code)
        .append(additionalProperties, rhs.additionalProperties).isEquals();
  }

}