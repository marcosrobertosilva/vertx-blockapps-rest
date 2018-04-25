package xyz.jetdrone.blockapps.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.BlocAPI;

import java.util.UUID;

public class CreateUserExample extends AbstractVerticle {

  @Override
  public void start() {

    // create a unique username with the prefix 'TEST'
    String username = "TEST-" + UUID.randomUUID();
    // choose a secure password. :)
    String password = "1234";

    // use blockapps-rest to create the user
    BlocAPI.create(vertx, config().getString("blocURL"))
      .postUser(username, new JsonObject().put("password", password), res -> {
        if (res.succeeded()) {
          String address = res.result();
          System.out.println("The address for " + username + " is " + address);
        }
      });
  }
}
