package xyz.jetdrone.blockapps.api;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.impl.BlocImpl;
import xyz.jetdrone.blockapps.api.impl.ExplorerImpl;

@VertxGen
public interface Explorer {

  static Explorer create(Vertx vertx, Config config) {
    return new ExplorerImpl(vertx, config);
  }

  @Fluent
  Explorer get(String url, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Explorer post(String url, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Explorer home(int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Explorer nodes(int node, Handler<AsyncResult<JsonObject>> handler);
}
