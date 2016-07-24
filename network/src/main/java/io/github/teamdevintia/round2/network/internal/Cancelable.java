package io.github.teamdevintia.round2.network.internal;

/**
 * @author Shad0wCore
 */
public interface Cancelable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();

}
