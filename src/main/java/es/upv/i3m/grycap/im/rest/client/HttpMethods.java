package es.upv.i3m.grycap.im.rest.client;

public enum HttpMethods {

  GET("GET"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE");

  private final String value;

  HttpMethods(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}