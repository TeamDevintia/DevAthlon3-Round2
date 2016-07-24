package io.github.teamdevintia.round2.network;

/**
 * @author Shad0wCore
 */
public interface Callback<T> {

    void trigger(T t);

}
