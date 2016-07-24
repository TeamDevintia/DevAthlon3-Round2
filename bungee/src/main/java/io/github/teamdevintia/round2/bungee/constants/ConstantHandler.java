package io.github.teamdevintia.round2.bungee.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shad0wCore
 * //TODO unused
 */
public abstract class ConstantHandler<T> {

    private Map<String, T> contentHashMap = new HashMap<>();
    private static Map<String, Object> rawContentHashMap = new HashMap<>();

    public abstract void initializeContent();

    public static Object raw(String identifier) {
        return rawContentHashMap.get(identifier);
    }

    protected void put(String identifier, T t) {
        contentHashMap.put(identifier, t);
        rawContentHashMap.put(identifier, t);
    }

    public T get(String identifier) {
        return this.contentMap().get(identifier);
    }

    public Map<String, T> contentMap() {
        return contentHashMap;
    }

    public void invalidateCaches() {
        contentHashMap.clear();
        contentHashMap = null;
    }

}
