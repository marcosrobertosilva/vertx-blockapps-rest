package xyz.jetdrone.blockapps;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class Node {

  private String blocUrl;
  private String explorerUrl;
  private String stratoUrl;
  private String searchUrl;

  public Node() {}

  public Node(JsonObject json) {
    NodeConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    NodeConverter.toJson(this, json);
    return json;
  }

  public String getBlocUrl() {
    return blocUrl;
  }

  public void setBlocUrl(String blocUrl) {
    this.blocUrl = blocUrl;
  }

  public String getExplorerUrl() {
    return explorerUrl;
  }

  public void setExplorerUrl(String explorerUrl) {
    this.explorerUrl = explorerUrl;
  }

  public String getStratoUrl() {
    return stratoUrl;
  }

  public void setStratoUrl(String stratoUrl) {
    this.stratoUrl = stratoUrl;
  }

  public String getSearchUrl() {
    return searchUrl;
  }

  public void setSearchUrl(String searchUrl) {
    this.searchUrl = searchUrl;
  }
}
