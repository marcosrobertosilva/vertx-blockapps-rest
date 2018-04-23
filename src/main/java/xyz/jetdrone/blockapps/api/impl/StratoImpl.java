package xyz.jetdrone.blockapps.api.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.Config;
import xyz.jetdrone.blockapps.api.Strato;

import static io.vertx.core.http.HttpMethod.*;
import static xyz.jetdrone.blockapps.api.impl.HttpClient.*;

public class StratoImpl implements Strato {

  private final HttpClient http;
  private final Config config;

  public StratoImpl(Vertx vertx, Config config) {
    this.http = new HttpClient(vertx);
    this.config = config;
  }

  @Override
  public Strato home(int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/", json(handler));
    return this;
  }

  @Override
  public Strato account(String address, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/account?address=" + urlEncode(address), json(handler));
    return this;
  }

  @Override
  public Strato block(long number, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/block?number=" + number, json(handler));
    return this;
  }

  @Override
  public Strato last(long number, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/block/last/" + number, json(handler));
    return this;
  }

  @Override
  public Strato transaction(JsonObject args, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/transaction?" + urlEncode(args), json(handler));
    return this;
  }

  @Override
  public Strato transactionLast(long number, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/transaction/last/" + number, json(handler));
    return this;
  }

  @Override
  public Strato transactionResult(String hash, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/transactionResult/" + urlEncode(hash), json(handler));
    return this;
  }

  @Override
  public Strato faucet(JsonObject body, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(POST, config.getNode(node).getStratoUrl() + "/eth/v1.2/faucet", FORM_URLENCODED, urlEncode(body), json(handler));
    return this;
  }

  @Override
  public Strato storage(String attr, String value, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/storage?" + urlEncode(attr) + "=" + urlEncode(value), json(handler));
    return this;
  }

  @Override
  public Strato search(String code, int node, Handler<AsyncResult<JsonObject>> handler) {
    http.fetch(GET, config.getNode(node).getStratoUrl() + "/eth/v1.2/account?code=" + urlEncode(code), json(handler));
    return this;
  }
}
