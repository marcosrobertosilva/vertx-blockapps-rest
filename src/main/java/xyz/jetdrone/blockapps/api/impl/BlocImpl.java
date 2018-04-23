package xyz.jetdrone.blockapps.api.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.Bloc;

import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;
import static xyz.jetdrone.blockapps.api.impl.HttpClient.*;

public class BlocImpl implements Bloc {

  private final HttpClient http;
  private final Config config;

  public BlocImpl(Vertx vertx, Config config) {
    this.http = new HttpClient(vertx);
    this.config = config;
  }

  @Override
  public Bloc contract(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(from) + "/" + urlEncode(address) + "/contract" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc uploadList(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(from) + "/" + urlEncode(address) + "/uploadList" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc require(String from, String address, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(from) + "/" + urlEncode(address) + "/import", JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc home(int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/", json(handler));
    return this;
  }

  @Override
  public Bloc call(String name, String address, String contractName, String contractAddress, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(name) + "/" + urlEncode(address) + "/contract/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/call" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc method(String name, String address, String contractName, String contractAddress, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(name) + "/" + urlEncode(address) + "/contract/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/call" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc callList(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(from) + "/" + urlEncode(address) + "/callList" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc send(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(from) + "/" + urlEncode(address) + "/send" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc sendList(String from, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(from) + "/" + urlEncode(address) + "/sendList" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc result(String hash, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/transactions/" + urlEncode(hash) + "/result" + (resolve ? "?resolve" : ""), json(handler));
    return this;
  }

  @Override
  public Bloc contracts(String name, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/contracts/" + urlEncode(name), json(handler));
    return this;
  }

  @Override
  public Bloc abi(String name, String address, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/contracts/" + urlEncode(name) + "/" + urlEncode(address), json(handler));
    return this;
  }

  @Override
  public Bloc state(String name, String address, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/contracts/" + urlEncode(name) + "/" + urlEncode(address) + "/state", json(handler));
    return this;
  }

  @Override
  public Bloc stateLookup(String name, String address, String mapping, String key, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/contracts/" + urlEncode(name) + "/" + urlEncode(address) + "/state/" + urlEncode(mapping) + "/" + urlEncode(key), json(handler));
    return this;
  }

  @Override
  public Bloc users(int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/users", json(handler));
    return this;
  }

  @Override
  public Bloc createUser(String name, JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(name), FORM_URLENCODED, urlEncode(body), json(handler));
    return this;
  }

  @Override
  public Bloc fill(String name, String address, JsonObject body, boolean resolve, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/users/" + urlEncode(name) + "/" + urlEncode(address) + "/fill" + (resolve ? "?resolve" : ""), FORM_URLENCODED, urlEncode(body), json(handler));
    return this;
  }

  @Override
  public Bloc compile(JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getBlocUrl() + "/contracts/compile", JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public Bloc search(String name, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/search/" + urlEncode(name) + "/state", json(handler));
    return this;
  }

  @Override
  public Bloc searchReduced(String name, JsonArray reducedProperties, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getBlocUrl() + "/search/" + urlEncode(name) + "state/reduced?" + urlEncode("props", reducedProperties), json(handler));
    return this;
  }

  @Override
  public JsonObject getTxParams() {
    return new JsonObject()
      .put("gasLimit", 100000000)
      .put("gasPrice", 1);
  }
}
