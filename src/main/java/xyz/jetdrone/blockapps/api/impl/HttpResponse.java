package xyz.jetdrone.blockapps.api.impl;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class HttpResponse {

  private final int statusCode;
  private final MultiMap headers;
  private final Buffer body;


  HttpResponse(int statusCode, MultiMap headers, Buffer body) {
    this.headers = headers;
    this.body = body;
    this.statusCode = statusCode;
  }

  public int statusCode() {
    return statusCode;
  }

  public MultiMap headers() {
    return headers;
  }

  public String getHeader(String header) {
    if (headers != null) {
      return headers.get(header);
    }
    return null;
  }

  public Buffer body() {
    return body;
  }

  public JsonObject jsonObject() {
    return new JsonObject(body.toString());
  }

  public JsonArray jsonArray() {
    return new JsonArray(body.toString());
  }

  public boolean is(String contentType) {
    if (headers != null) {
      String header = headers.get("Content-Type");
      if (header != null) {
        int sep = header.indexOf(';');
        // exclude charset
        if (sep != -1) {
          header = header.substring(0, sep).trim();
        }

        if (contentType.equals(header)) {
          return true;
        }
      }
    }
    return false;
  }
}
