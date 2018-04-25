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
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import xyz.jetdrone.blockapps.BlocAPI;

import static io.vertx.core.http.HttpMethod.*;

public class BlocAPIImpl extends HttpClient implements BlocAPI {

  private final String baseURL;

  public BlocAPIImpl(Vertx vertx, String baseURL) {
    super(vertx);
    this.baseURL = baseURL + "/bloc/v2.2";
  }

  @Override
  public BlocAPI getUsers(Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/users", array(handler));
    return this;
  }

  @Override
  public BlocAPI getUser(String user, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/users/" + urlEncode(user), array(handler));
    return this;
  }

  @Override
  public BlocAPI postUser(String user, JsonObject body, Handler<AsyncResult<String>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user), FORM_URLENCODED, urlEncode(body), text(handler));
    return this;
  }

  @Override
  public BlocAPI postUserFill(String user, String address, boolean resolve, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(address) + "/fill" + (resolve ? "?resolve" : ""), json(handler));
    return this;
  }

  @Override
  public BlocAPI postUserSend(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(address) + "/send" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public BlocAPI postUserContract(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(address) + "/contract" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public BlocAPI postUserUploadList(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonArray>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(address) + "/uploadList" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), array(handler));
    return this;
  }

  @Override
  public BlocAPI postUserContractCall(String user, String userAddress, String contractName, String contractAddress, JsonObject body, boolean resolve, Handler<AsyncResult<JsonObject>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(userAddress) + "/contract/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/call" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), json(handler));
    return this;
  }

  @Override
  public BlocAPI postUserSendList(String user, String userAddress, JsonObject body, boolean resolve, Handler<AsyncResult<JsonArray>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(userAddress) + "/sendList" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), array(handler));
    return this;
  }

  @Override
  public BlocAPI postUserCallList(String user, String address, JsonObject body, boolean resolve, Handler<AsyncResult<JsonArray>> handler) {
    fetch(POST, baseURL + "/users/" + urlEncode(user) + "/" + urlEncode(address) + "/callList" + (resolve ? "?resolve" : ""), JSON, body.toBuffer(), array(handler));
    return this;
  }

  @Override
  public BlocAPI getAddresses(Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/addresses", array(handler));
    return this;
  }

  @Override
  public BlocAPI getContracts(Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/contracts", json(handler));
    return this;
  }

  @Override
  public BlocAPI getContract(String contractName, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName), array(handler));
    return this;
  }

  @Override
  public BlocAPI getContract(String contractName, String contractAddress, Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/" + urlEncode(contractAddress), json(handler));
    return this;
  }

  @Override
  public BlocAPI getContractState(String contractName, String contractAddress, JsonObject query, Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/state" + urlEncode(query), json(handler));
    return this;
  }

  @Override
  public BlocAPI getContractDetails(String contractAddress, Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/contracts/contract/" + urlEncode(contractAddress) + "/details", json(handler));
    return this;
  }

  @Override
  public BlocAPI getContractFunctions(String contractName, String contractAddress, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/functions", array(handler));
    return this;
  }

  @Override
  public BlocAPI getContractSymbols(String contractName, String contractAddress, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/symbols", array(handler));
    return this;
  }

  @Override
  public BlocAPI getContractState(String contractName, String contractAddress, String mapping, String key, Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/state/" + urlEncode(mapping) + "/" + urlEncode(key), json(handler));
    return this;
  }

  @Override
  public BlocAPI getContractStates(String contractName, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/all/states", array(handler));
    return this;
  }

  @Override
  public BlocAPI getContractEnum(String contractName, String contractAddress, String enumName, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/contracts/" + urlEncode(contractName) + "/" + urlEncode(contractAddress) + "/enum/" + urlEncode(enumName), array(handler));
    return this;
  }

  @Override
  public BlocAPI postContractCompile(JsonArray body, Handler<AsyncResult<JsonArray>> handler) {
    fetch(POST, baseURL + "/contracts/compile", JSON, body.toBuffer(), array(handler));
    return this;
  }

  @Override
  public BlocAPI getSearch(String contractName, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/search/" + urlEncode(contractName), array(handler));
    return this;
  }

  @Override
  public BlocAPI getSearchState(String contractName, Handler<AsyncResult<JsonArray>> handler) {
    fetch(GET, baseURL + "/search/" + urlEncode(contractName) + "/state", array(handler));
    return this;
  }

  @Override
  public BlocAPI getSearchReducedState(String contractName, JsonArray query, Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/search/" + urlEncode(contractName) + "/state/reduced" + urlEncode("props", query), json(handler));
    return this;
  }

  @Override
  public BlocAPI getTransactionResult(String hash, boolean resolve, Handler<AsyncResult<JsonObject>> handler) {
    fetch(GET, baseURL + "/transactions/" + urlEncode(hash) + "/result" + (resolve ? "?resolve" : ""), json(handler));
    return this;
  }

  @Override
  public BlocAPI postTransactionResults(boolean resolve, Handler<AsyncResult<JsonArray>> handler) {
    fetch(POST, baseURL + "/transactions/results" + (resolve ? "?resolve" : ""), array(handler));
    return this;
  }
}
