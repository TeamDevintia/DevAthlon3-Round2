package io.github.teamdevintia.round2.network.pipeline;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Shad0wCore
 */
public class MessageDeserializer {

    private String rootName;

    public MessageDeserializer(String rootName) {
        this.rootName = rootName;
    }

    public JSONObject deserialize(String jsonString) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject rootObject = (JSONObject) jsonParser.parse(jsonString);
        return ((JSONObject) rootObject.get(this.rootName));
    }

    public void recompose(String rootName) {
        this.rootName = rootName;
    }

}
