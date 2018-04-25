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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.impl.BlocAPIImpl;

@VertxGen
public interface BlocAPI {

  static BlocAPI create(Vertx vertx, String baseURL) {
    return new BlocAPIImpl(vertx, baseURL);
  }

  @Fluent
  BlocAPI getUsers(Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getUser(String user, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI postUser(String user, JsonObject body, Handler<AsyncResult<String>> handler);

  @Fluent
  default BlocAPI postUserFill(String user, String address, Handler<AsyncResult<JsonObject>> handler) {
    return postUserFill(user, address, false, handler);
  }

  @Fluent
  BlocAPI postUserFill(String user, String address, boolean resolve, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  default BlocAPI postUserSend(String user, String address, JsonObject body, Handler<AsyncResult<JsonObject>> handler) {
    return postUserSend(user, address, body, false, handler);
  }

  @Fluent
  BlocAPI postUserSend(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  default BlocAPI postUserContract(String user, String address, JsonObject body, Handler<AsyncResult<JsonObject>> handler) {
    return postUserContract(user, address, body, false, handler);
  }

  @Fluent
  BlocAPI postUserContract(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  default BlocAPI postUserUploadList(String user, String address, JsonObject body, Handler<AsyncResult<JsonArray>> handler) {
    return postUserUploadList(user, address, body, false, handler);
  }

  @Fluent
  BlocAPI postUserUploadList(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  default BlocAPI postUserContractCall(String user, String userAddress, String contractName, String contractAddress, JsonObject body, Handler<AsyncResult<JsonObject>> handler) {
    return  postUserContractCall(user, userAddress, contractName, contractAddress, body, false, handler);
  }

  @Fluent
  BlocAPI postUserContractCall(String user, String userAddress, String contractName, String contractAddress, JsonObject body, boolean resolve, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  default BlocAPI postUserSendList(String user, String userAddress, JsonObject body, Handler<AsyncResult<JsonArray>> handler) {
    return postUserSendList(user, userAddress, body, false, handler);
  }

  @Fluent
  BlocAPI postUserSendList(String user, String userAddress, JsonObject body, boolean resolve, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  default BlocAPI postUserCallList(String user, String address, JsonObject body, Handler<AsyncResult<JsonArray>> handler) {
    return postUserCallList(user, address, body, false, handler);
  }

  @Fluent
  BlocAPI postUserCallList(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getAddresses(Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getContracts(Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  BlocAPI getContract(String contractName, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getContract(String contractName, String contractAddress, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  BlocAPI getContractState(String contractName, String contractAddress, JsonObject query, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  BlocAPI getContractDetails(String contractAddress, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  BlocAPI getContractFunctions(String contractName, String contractAddress, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getContractSymbols(String contractName, String contractAddress, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getContractState(String contractName, String contractAddress, String mapping, String key, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  BlocAPI getContractStates(String contractName, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getContractEnum(String contractName, String contractAddress, String enumName, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI postContractCompile(JsonArray body, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getSearch(String contractName, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getSearchState(String contractName, Handler<AsyncResult<JsonArray>> handler);

  @Fluent
  BlocAPI getSearchReducedState(String contractName, JsonArray query, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  default BlocAPI getTransactionResult(String hash, Handler<AsyncResult<JsonObject>> handler) {
    return getTransactionResult(hash, false, handler);
  }

  @Fluent
  BlocAPI getTransactionResult(String hash, boolean resolve, Handler<AsyncResult<JsonObject>> handler);

  @Fluent
  default BlocAPI postTransactionResults(Handler<AsyncResult<JsonArray>> handler) {
    return postTransactionResults(false, handler);
  }

  @Fluent
  BlocAPI postTransactionResults(boolean resolve, Handler<AsyncResult<JsonArray>> handler);
}
