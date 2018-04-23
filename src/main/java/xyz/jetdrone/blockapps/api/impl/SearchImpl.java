package xyz.jetdrone.blockapps.api.impl;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.Search;

import static xyz.jetdrone.blockapps.api.impl.HttpClient.*;

public class SearchImpl implements Search {

  private final HttpClient http;
  private final Config config;

  public SearchImpl(Vertx vertx, Config config) {
    this.http = new HttpClient(vertx);
    this.config = config;
  }

  @Override
  public Search get(String url, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.GET, config.getNode(node).getSearchUrl() + "/" + url, json(handler));
    return this;
  }

  @Override
  public Search post(String url, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.POST, config.getNode(node).getSearchUrl() + "/" + url, JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Search query(String query, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(HttpMethod.GET, config.getNode(node).getSearchUrl() + "/" + query, json(handler));
    return this;
  }
}
