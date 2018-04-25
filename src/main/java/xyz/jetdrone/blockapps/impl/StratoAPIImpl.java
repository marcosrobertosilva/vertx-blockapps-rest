/*
 * Copyright 2018 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package xyz.jetdrone.blockapps.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.StratoAPI;

import static io.vertx.core.http.HttpMethod.*;

public class StratoAPIImpl extends HttpClient implements StratoAPI {

  private final String baseURL;

  public StratoAPIImpl(Vertx vertx, String baseURL) {
    super(vertx);
    this.baseURL = baseURL + "/eth/v1.2";
  }

  @Override
  public StratoAPI getTransaction(JsonObject query, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/transaction" + urlEncode(query), array(handler));
    return this;
  }

  @Override
  public StratoAPI postTransaction(JsonObject body, Handler<AsyncResult<String>> handler) {
    fetch(POST, baseURL + "/transaction", JSON, body.toBuffer(), text(handler));
    return this;
  }

  @Override
  public StratoAPI getLastTransaction(int integer, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/transaction/last/" + integer, array(handler));
    return this;
  }

  @Override
  public StratoAPI postTransactionList(JsonArray body, Handler<AsyncResult<JsonArray>> handler) {
    fetch(POST, baseURL + "/transactionList", JSON, body.toBuffer(), array(handler));
    return this;
  }

  @Override
  public StratoAPI getTransactionResult(String hash, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/transactionResult/" + urlEncode(hash), array(handler));
    return this;
  }

  @Override
  public StratoAPI postTransactionBatch(JsonArray body, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/transactionResult/batch", JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public StratoAPI getBlock(JsonObject query, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/block" + urlEncode(query), array(handler));
    return this;
  }

  @Override
  public StratoAPI getLastBlock(int integer, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/block/last/" + integer, array(handler));
    return this;
  }

  @Override
  public StratoAPI getAccount(JsonObject query, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/account" + urlEncode(query), array(handler));
    return this;
  }

  @Override
  public StratoAPI getDifficultyStats(Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/stats/difficulty", json(handler));
    return this;
  }

  @Override
  public StratoAPI getTotalTXStats(Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/stats/totaltx", json(handler));
    return this;
  }

  @Override
  public StratoAPI getStorage(JsonObject query, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/storage" + urlEncode(query), array(handler));
    return this;
  }

  @Override
  public StratoAPI postFaucet(JsonObject body, Handler<AsyncResult<String>> handler) {
    fetch(POST, baseURL + "/faucet", FORM_URLENCODED, urlEncode(body), text(handler));
    return this;
  }

  @Override
  public StratoAPI postSolc(Buffer body, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/solc", FORM_URLENCODED, body, json(handler));
    return this;
  }

  @Override
  public StratoAPI postExtABI(Buffer body, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/extabi", FORM_URLENCODED, body, json(handler));
    return this;
  }
}
