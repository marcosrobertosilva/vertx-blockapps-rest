package xyz.jetdrone.blockapps;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@VertxGen
public interface BlockApps {

  @Fluent
  default BlockApps getEnum(String path, String name) {
    throw new UnsupportedOperationException("No ethereum parser code available yet!");
  }

  @Fluent
  default BlockApps getEnums(String path) {
    throw new UnsupportedOperationException("No ethereum parser code available yet!");
  }

  @Fluent
  BlockApps getState(Contract contract, int node, Handler<AsyncResult<JsonObject>> handler);


  /**
   * This function creates a user with given name and password on a given node
   *
   * @param name the desired username
   * @param password the user's password
   * @param isAsync if async returns with no faucet
   * @param node target node
   * @param handler callback handler with the user
   * @return self
   */
  @Fluent
  BlockApps createUser(String name, String password, boolean isAsync, int node, Handler<AsyncResult<User>> handler);
}
