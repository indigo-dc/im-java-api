package es.upv.i3m.grycap.im.pojo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseError {

  private final String message;
  private final Integer code;

  public ResponseError(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(message).append(code).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (other instanceof ResponseError) {
      ResponseError rhs = (ResponseError) other;
      return new EqualsBuilder().append(message, rhs.message)
          .append(code, rhs.code).isEquals();
    }
    return false;
  }
}