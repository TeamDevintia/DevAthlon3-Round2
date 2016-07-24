package io.github.teamdevintia.round2.network.internal;

import io.github.teamdevintia.round2.network.exceptions.IllegalPipelineEvent;
import io.github.teamdevintia.round2.network.exceptions.IllegalUsageException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author Shad0wCore
 */
public final class EventBus {

    private final Set<PacketEventHandler> staticEventHandlers = new HashSet<>();
    private final HashMap<String, PacketEventHandler> definedEventHandlers = new HashMap<>();

    public void registerEvent(PacketEventHandler eventHandler) {
        this.staticEventHandlers.add(eventHandler);
    }

    public void registerDefinedEvent(String id, PacketEventHandler eventHandler) throws IllegalUsageException {
        if (this.definedEventHandlers.get(id) != null) {
            this.definedEventHandlers.put(id, eventHandler);
        } else {
            throw new IllegalUsageException("Event Handler with ID: " + id + " is already registered. Override denied!");
        }
    }

    public final void callEvent(Event eventClass) {
        for (PacketEventHandler eventHandler : staticEventHandlers) {
            for (Method pipelineMethod : this.containsEventPipelines(eventHandler.getPipelineEventListener().getClass(), PipelineEvent.class)) {
                try {
                    if (this.eventEquals(eventClass, pipelineMethod.getParameters())) {
                        eventHandler.getEventExecutor().executeCalled();
                        pipelineMethod.invoke(eventHandler.getPipelineEventListener(), eventClass);
                    }
                } catch (IllegalPipelineEvent | InvocationTargetException | IllegalAccessException illegalEventPipeline) {
                    illegalEventPipeline.printStackTrace();
                }
            }
        }
    }

    private List<Method> containsEventPipelines(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Method> pipelineMethods = new ArrayList<>(clazz.getMethods().length);
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                pipelineMethods.add(method);
            }
        }
        return pipelineMethods;
    }

    private boolean eventEquals(Event eventClass, Parameter[] parameters) throws IllegalPipelineEvent {
        if (parameters.length > 1)
            throw new IllegalPipelineEvent("Event Method has more than 1 parameter - Parameter length: " + parameters.length);
        return eventClass.getClass().isAssignableFrom(parameters[0].getType());
    }

}
