package xyz.jetdrone.blockapps.api;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.impl.BlocImpl;

@VertxGen
public interface Bloc {

  static Bloc create(Vertx vertx, Config config) {
    return new BlocImpl(vertx, config);
  }

  @Fluent
  Bloc contract(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  Bloc uploadList(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc require(String from, String address, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc home(int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc call(String name, String address, String contractName, String contractAddress, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc method(String name, String address, String contractNAme, String contractAddress, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc callList(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc send(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc sendList(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc result(String hash, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc contracts(String name, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc abi(String name, String address, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc state(String name, String address, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc stateLookup(String name, String address, String mapping, String key, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc users(int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc createUser(String name, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc fill(String name, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc compile(JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc search(String name, int node, Handler<AsyncResult<JsonObject>> handler);
  @Fluent
  Bloc searchReduced(String name, JsonArray reducedProperties, int node, Handler<AsyncResult<JsonObject>> handler);

  JsonObject getTxParams();
}
