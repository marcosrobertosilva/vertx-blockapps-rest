package xyz.jetdrone.blockapps.api.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.Explorer;

import static xyz.jetdrone.blockapps.api.impl.HttpClient.*;

public class ExplorerImpl implements Explorer {

  private final HttpClient http;
  private final Config config;

  public ExplorerImpl(Vertx vertx, Config config) {
    this.http = new HttpClient(vertx);
    this.config = config;
  }

  @Override
  public Explorer get(String url, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.GET, config.getNode(node).getExplorerUrl() + "/" + url, json(handler));
    return this;
  }

  @Override
  public Explorer post(String url, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.POST, config.getNode(node).getExplorerUrl() + "/" + url, JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Explorer home(int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.GET, config.getNode(node).getExplorerUrl() + "/", json(handler));
    return this;
  }

  @Override
  public Explorer nodes(int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.GET, config.getNode(node).getExplorerUrl() + "/api/nodes", json(handler));
    return this;
  }
}
