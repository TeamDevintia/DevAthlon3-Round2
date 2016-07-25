package io.github.teamdevintia.round2.client.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * @author Shad0wCore
 */
public class JsonUtil {

    private static JSONParser jsonParser = new JSONParser();

    public static synchronized void write(String formattedJson, String destinationPath) throws ParseException, IOException {
        write(formattedJson, new File(destinationPath));
    }

    public static synchronized void write(String formattedJson, File destination) throws ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        write((JSONObject) jsonParser.parse(formattedJson), destination.getAbsolutePath());
    }

    public static synchronized void write(JSONObject rootObject, String destinationPath) throws IOException {
        write(rootObject, new File(destinationPath));
    }

    public static synchronized void write(JSONObject rootObject, File destination) throws IOException {
        FileWriter fileWriter = new FileWriter(destination);
        fileWriter.write(rootObject.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static synchronized JSONObject from(Reader reader) throws IOException, ParseException {
        return (JSONObject) jsonParser.parse(reader);
    }

    public static synchronized JSONObject from(InputStream inputStream) throws IOException, ParseException {
        return from(new InputStreamReader(inputStream));
    }

    public static synchronized JSONObject from(File file) throws IOException, ParseException {
        return from(new FileReader(file));
    }

    public static synchronized JSONObject from(InputStream inputStream, String dotPath) throws IOException, ParseException {
        return from(new InputStreamReader(inputStream), dotPath);
    }

    public static synchronized JSONObject from(File file, String dotPath) throws IOException, ParseException {
        return from(new FileReader(file), dotPath);
    }

    public static synchronized JSONObject from(String formattedJson, String dotPath) throws ParseException {
        String[] pathSplits = dotPath.split("\\.");
        JSONObject root = (JSONObject) jsonParser.parse(formattedJson);
        JSONObject lastObject = null;

        for (int i = 0; i < pathSplits.length - 1; i++) {
            if (i == 0) {
                lastObject = (JSONObject) root.get(pathSplits[i]);
                continue;
            }

            lastObject = (JSONObject) lastObject.get(pathSplits[i]);
        }

        return lastObject != null ? (JSONObject) lastObject.get(pathSplits[pathSplits.length - 1]) : null;
    }

    public static synchronized JSONObject from(Reader reader, String dotPath) throws IOException, ParseException {
        Reader copiedReader = reader;

        String[] pathSplits = dotPath.split("\\.");
        JSONObject root = (JSONObject) jsonParser.parse(copiedReader);
        JSONObject lastObject = null;

        for (int i = 0; i < pathSplits.length - 1; i++) {
            if (i == 0) {
                lastObject = (JSONObject) root.get(pathSplits[i]);
                continue;
            }

            lastObject = (JSONObject) lastObject.get(pathSplits[i]);
        }

        return lastObject != null ? (JSONObject) lastObject.get(pathSplits[pathSplits.length - 1]) : null;
    }

}
