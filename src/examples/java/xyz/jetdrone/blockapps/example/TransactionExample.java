package xyz.jetdrone.blockapps.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.BlocAPI;

import java.util.UUID;

public class TransactionExample extends AbstractVerticle {

  @Override
  public void start() {

    // create a unique username with the prefix 'Alice'
    String alice = "Alice-" + UUID.randomUUID();
    // create a unique username with the prefix 'Bob'
    String bob = "Bob-" + UUID.randomUUID();
    // choose a secure password. :)
    String password = "1234";

    // use bloc API
    BlocAPI bloc = BlocAPI.create(vertx, config().getString("blocURL"));

    CompositeFuture.all(
      // create user Alice
      Future.<String>future(f -> bloc.postUser(alice, new JsonObject().put("password", password), f.completer())),
      // create user Bob
      Future.<String>future(f -> bloc.postUser(bob, new JsonObject().put("password", password), f.completer()))
    ).setHandler(userCreation -> {
      if (userCreation.succeeded()) {
        // get Alice and Bob's balances
        CompositeFuture.all(
          // create user Alice
          Future.<JsonArray>future(f -> bloc.getUser(userCreation.result().resultAt(0), f.completer())),
          // create user Bob
          Future.<JsonArray>future(f -> bloc.getUser(userCreation.result().resultAt(1), f.completer()))
        ).setHandler(balanceLoad -> {
          if (balanceLoad.succeeded()) {
            // print balance
            JsonArray aliceUser = balanceLoad.result().resultAt(0);
            JsonArray bobUser = balanceLoad.result().resultAt(1);

            System.out.println("Balance for Alice is " + aliceUser.getJsonObject(0).getValue("balance"));
            System.out.println("Balance for Bob is " + bobUser.getJsonObject(0).getValue("balance"));
          }
        });
      }
    });
  }
}
