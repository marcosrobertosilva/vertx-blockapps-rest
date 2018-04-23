package xyz.jetdrone.blockapps.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.BlockApps;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.Contract;
import xyz.jetdrone.blockapps.User;
import xyz.jetdrone.blockapps.api.Bloc;

public class BlockAppsImpl implements BlockApps {

  private final Bloc bloc;

  public BlockAppsImpl(Vertx vertx, Config config) {
    bloc = Bloc.create(vertx, config);
  }

  @Override
  public BlockApps getState(Contract contract, int node, Handler<AsyncResult<JsonObject>> handler) {
    bloc.state(contract.getName(), contract.getAddress(), node, handler);
    return this;
  }

  @Override
  public BlockApps createUser(String name, String password, boolean isAsync, int node, Handler<AsyncResult<User>> handler) {
    bloc.createUser(name, new JsonObject().put("password", password), node, createUser -> {
      if (createUser.failed()) {
        handler.handle(Future.failedFuture(createUser.cause()));
        return;
      }
      // TODO: validate the address

      User user = new User()
        .setName(name)
        .setPassword(password);
//        .setAddress(createUser.result());

      // if isAsync - return with no faucet fill call
      if (isAsync) {
        handler.handle(Future.succeededFuture(user));
        return;
      }

      // otherwise - block for faucet fill call
      bloc.fill(user.getName(), user.getAddress(), new JsonObject(), true, node, fill -> {
        if (fill.failed()) {
          handler.handle(Future.failedFuture(fill.cause()));
          return;
        }

        handler.handle(Future.succeededFuture(new User(fill.result())));
      });
    });
    return this;
  }
}
