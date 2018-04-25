/*
 * Copyright 2018 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package xyz.jetdrone.blockapps.impl;

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
