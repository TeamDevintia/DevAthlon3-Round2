package io.github.teamdevintia.round2.network.internal.event;

/**
 * @author Shad0wCore
 */
public interface Cancelable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();

}
