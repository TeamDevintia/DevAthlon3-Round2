package io.github.teamdevintia.round2.network.pipeline;

import org.json.simple.JSONObject;

/**
 * @author Shad0wCore
 */
public class MessageSerializer {

    private JSONObject rootObject = new JSONObject();
    private JSONObject childObject;

    public MessageSerializer(String rootName) {
        this.rootObject.put(rootName, childObject = new JSONObject());
    }

    public MessageSerializer addProperty(String propertyName, Object property) {
        this.childObject.put(propertyName, property);
        return this;
    }

    public String serialize() {
        return this.rootObject.toJSONString();
    }

    public void recompose(String rootName) {
        rootObject = new JSONObject();
        rootObject.put(rootName, childObject = new JSONObject());
    }

}
