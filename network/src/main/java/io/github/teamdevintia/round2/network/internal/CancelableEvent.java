package io.github.teamdevintia.round2.network.internal;

/**
 * @author Shad0wCore
 */
public abstract class CancelableEvent extends Event implements Cancelable {

    private boolean cancelled = false;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}
