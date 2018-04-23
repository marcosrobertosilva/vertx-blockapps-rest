package xyz.jetdrone.blockapps;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

@DataObject(generateConverter = true)
public class Config {

  private List<Node> nodes;

  public Config() {}

  public Config(JsonObject json) {
    ConfigConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    final JsonObject json = new JsonObject();
    ConfigConverter.toJson(this, json);
    return json;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

  public Node getNode(int idx) {
    return nodes.get(idx);
  }
}
