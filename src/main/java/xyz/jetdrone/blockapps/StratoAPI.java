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
package xyz.jetdrone.blockapps;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.impl.StratoAPIImpl;

@VertxGen
public interface StratoAPI {

  static StratoAPI create(Vertx vertx, String baseURL) {
    return new StratoAPIImpl(vertx, baseURL);
  }

  @Fluent
  StratoAPI getTransaction(JsonObject query, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI postTransaction(JsonObject body, Handler<AsyncResult<String>> handler);

  @Fluent
  StratoAPI getLastTransaction(int integer, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI postTransactionList(JsonArray body, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI getTransactionResult(String hash, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI postTransactionBatch(JsonArray body, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  StratoAPI getBlock(JsonObject query, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI getLastBlock(int integer, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI getAccount(JsonObject query, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI getDifficultyStats(Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  StratoAPI getTotalTXStats(Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  StratoAPI getStorage(JsonObject query, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  StratoAPI postFaucet(JsonObject body, Handler<AsyncResult<String>> handler);

  @Fluent
  StratoAPI postSolc(Buffer body, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  StratoAPI postExtABI(Buffer body, Handler<AsyncResult<JsonObject>> handler);
}
