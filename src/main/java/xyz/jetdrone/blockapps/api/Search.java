package xyz.jetdrone.blockapps.api;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.impl.SearchImpl;

@VertxGen
public interface Search {

  static Search create(Vertx vertx, Config config) {
    return new SearchImpl(vertx, config);
  }

  @Fluent
  Search get(String url, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Search post(String url, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Search query(String query, int node, Handler<AsyncResult<JsonObject>> handler);
}
