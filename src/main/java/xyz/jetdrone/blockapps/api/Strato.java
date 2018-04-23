package xyz.jetdrone.blockapps.api;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.impl.StratoImpl;

@VertxGen
public interface Strato {

  static Strato create(Vertx vertx, Config config) {
    return new StratoImpl(vertx, config);
  }

  @Fluent
  Strato home(int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato account(String address, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato block(long number, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato last(long number, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato transaction(JsonObject args, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato transactionLast(long number, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato transactionResult(String hash, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato faucet(JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato storage(String attr, String value, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Strato search(String code, int node, Handler<AsyncResult<JsonObject>> handler);
}
