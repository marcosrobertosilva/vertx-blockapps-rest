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

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

class HttpClient {

  static final MultiMap JSON = MultiMap.caseInsensitiveMultiMap().add("Content-Type", "application/json");
  static final MultiMap FORM_URLENCODED = MultiMap.caseInsensitiveMultiMap().add("Content-Type", "application/x-www-form-urlencoded");

  private final Vertx vertx;
  private final HttpClientOptions defaultOptions;

  HttpClient(Vertx vertx) {
    this(vertx, new HttpClientOptions());
  }

  HttpClient(Vertx vertx, HttpClientOptions options) {
    this.vertx = vertx;
    this.defaultOptions = options;
  }

  public void fetch(String url, Handler<AsyncResult<HttpResponse>> callback) {
    fetch(HttpMethod.GET, url, null, null, callback);
  }

  public void fetch(HttpMethod method, String url, Handler<AsyncResult<HttpResponse>> callback) {
    fetch(method, url, null, null, callback);
  }

  public void fetch(HttpMethod method, String url, MultiMap headers, Handler<AsyncResult<HttpResponse>> callback) {
    fetch(method, url, headers, null, callback);
  }

  public void fetch(HttpMethod method, String url, Buffer payload, Handler<AsyncResult<HttpResponse>> callback) {
    fetch(method, url, null, payload, callback);
  }

  public void fetch(HttpMethod method, String url, MultiMap headers, Buffer payload, Handler<AsyncResult<HttpResponse>> callback) {
    // create a request
    final HttpClientRequest request = makeRequest(method, url, callback);

    // apply the provided headers
    if (headers != null) {
      request.headers().setAll(headers);
    }

    if (payload != null) {
      if (method == HttpMethod.POST || method == HttpMethod.PATCH || method == HttpMethod.PUT) {
        request.putHeader("Content-Length", Integer.toString(payload.length()));
        request.write(payload);
      }
    }

    // Make sure the request is ended when you're done with it
    request.end();
  }

  private HttpClientRequest makeRequest(HttpMethod method, String uri, final Handler<AsyncResult<HttpResponse>> callback) {
    io.vertx.core.http.HttpClient client;

    try {
      URL url = new URL(uri);
      boolean isSecure = "https".equalsIgnoreCase(url.getProtocol());
      String host = url.getHost();
      int port = url.getPort();

      if (port == -1) {
        if (isSecure) {
          port = 443;
        } else {
          port = 80;
        }
      }

      client = vertx.createHttpClient(new HttpClientOptions(defaultOptions)
        .setSsl(isSecure)
        .setDefaultHost(host)
        .setDefaultPort(port));

    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    final HttpClientRequest request = client.requestAbs(method, uri, resp -> {
      resp.exceptionHandler(t -> {
        callback.handle(Future.failedFuture(t));
        client.close();
      });

      resp.bodyHandler(body -> {
        if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
          if (body == null || body.length() == 0) {
            callback.handle(Future.failedFuture(resp.statusMessage()));
          } else {
            callback.handle(Future.failedFuture(resp.statusMessage() + ": " + body.toString()));
          }
        } else {
          callback.handle(Future.succeededFuture(new HttpResponse(resp.statusCode(), resp.headers(), body)));
        }
        client.close();
      });
    });

    request.exceptionHandler(t -> {
      callback.handle(Future.failedFuture(t));
      client.close();
    });

    return request;
  }

  public static Buffer urlEncode(JsonObject json) {

    final Buffer buffer = Buffer.buffer();

    if (json == null) {
      return buffer;
    }

    boolean empty = true;

    try {
      for (Map.Entry<String, ?> kv : json) {
        if (empty) {
          buffer.appendByte((byte) '?');
        } else {
          buffer.appendByte((byte) '&');
        }
        buffer.appendString(URLEncoder.encode(kv.getKey(), "UTF-8"));
        buffer.appendByte((byte) '=');
        Object v = kv.getValue();
        if (v != null) {
          buffer.appendString(URLEncoder.encode(v.toString(), "UTF-8"));
        }
        empty = false;
      }
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    return buffer;
  }

  public static Buffer urlEncode(String key, JsonArray json) {
    Buffer buffer = Buffer.buffer();
    boolean empty = true;

    try {
      key = URLEncoder.encode(key, "UTF-8");

      for (Object v : json) {
        if (empty) {
          buffer.appendByte((byte) '?');
        } else {
          buffer.appendByte((byte) '&');
        }
        buffer.appendString(key);
        buffer.appendByte((byte) '=');
        if (v != null) {
          buffer.appendString(URLEncoder.encode(v.toString(), "UTF-8"));
        }
        empty = false;
      }
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    return buffer;
  }

  public static String urlEncode(String value) {

    if (value == null) {
      return "";
    }

    try {
      return URLEncoder.encode(value, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static JsonObject urlDecode(String query) throws UnsupportedEncodingException {
    final JsonObject json = new JsonObject();
    final String[] pairs = query.split("&");
    for (String pair : pairs) {
      final int idx = pair.indexOf("=");
      final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
      final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
      if (!json.containsKey(key)) {
        json.put(key, value);
      } else {
        Object oldValue = json.getValue(key);
        JsonArray array;
        if (oldValue instanceof JsonArray) {
          array = (JsonArray) oldValue;
        } else {
          array = new JsonArray();
          array.add(oldValue);
          json.put(key, array);
        }
        if (value == null) {
          array.addNull();
        } else {
          array.add(value);
        }
      }
    }

    return json;
  }

  public static Handler<AsyncResult<HttpResponse>> json(Handler<AsyncResult<JsonObject>> handler) {
    return fetch -> {
      if (fetch.failed()) {
        handler.handle(Future.failedFuture(fetch.cause()));
      } else {
        if (fetch.result().is("application/json")) {
          JsonObject json;
          try {
            json = fetch.result().jsonObject();
          } catch (RuntimeException e) {
            handler.handle(Future.failedFuture(e));
            return;
          }
          handler.handle(Future.succeededFuture(json));
          return;
        }
        // unknown type
        handler.handle(Future.failedFuture("Unknown content type: " + fetch.result().getHeader("Content-Type")));
      }
    };
  }

  public static Handler<AsyncResult<HttpResponse>> array(Handler<AsyncResult<JsonArray>> handler) {
    return fetch -> {
      if (fetch.failed()) {
        handler.handle(Future.failedFuture(fetch.cause()));
      } else {
        if (fetch.result().is("application/json")) {
          JsonArray json;
          try {
            json = fetch.result().jsonArray();
          } catch (RuntimeException e) {
            handler.handle(Future.failedFuture(e));
            return;
          }
          handler.handle(Future.succeededFuture(json));
          return;
        }
        // unknown type
        handler.handle(Future.failedFuture("Unknown content type: " + fetch.result().getHeader("Content-Type")));
      }
    };
  }

  public static Handler<AsyncResult<HttpResponse>> text(Handler<AsyncResult<String>> handler) {
    return text("UTF8", handler);
  }

  public static Handler<AsyncResult<HttpResponse>> text(String enc, Handler<AsyncResult<String>> handler) {
    return fetch -> {
      if (fetch.failed()) {
        handler.handle(Future.failedFuture(fetch.cause()));
      } else {
        Buffer buffer = fetch.result().body();
        if (buffer == null) {
          handler.handle(Future.succeededFuture());
        } else {
          if (fetch.result().is("application/json")) {
            try {
              handler.handle(Future.succeededFuture(Json.decodeValue(buffer, String.class)));
            } catch (RuntimeException e) {
              handler.handle(Future.failedFuture(e));
              return;
            }
          } else {
            handler.handle(Future.succeededFuture(buffer.toString(enc)));
          }
        }
      }
    };
  }

  public static Handler<AsyncResult<HttpResponse>> binary(Handler<AsyncResult<Buffer>> handler) {
    return fetch -> {
      if (fetch.failed()) {
        handler.handle(Future.failedFuture(fetch.cause()));
      } else {
        handler.handle(Future.succeededFuture(fetch.result().body()));
      }
    };
  }
}
