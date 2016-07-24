package io.github.teamdevintia.round2.network.internal;

/**
 * @author Shad0wCore
 */
public final class EventHandler {

    private EventExecutor eventExecutor;
    private PipelineEventListener pipelineEventListener;
    private boolean ignoreCancelled;
    private String identifier;

    public EventHandler(PipelineEventListener pipelineEventListener, EventExecutor eventExecutor, String identifier) {
        this.pipelineEventListener = pipelineEventListener;
        this.eventExecutor = eventExecutor;
        this.identifier = identifier;
    }

    public EventExecutor getEventExecutor() {
        return eventExecutor;
    }

    public PipelineEventListener getPipelineEventListener() {
        return pipelineEventListener;
    }

    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

    public String getIdentifier() {
        return identifier;
    }
}
