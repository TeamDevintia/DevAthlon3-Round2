package io.github.teamdevintia.round2.network.internal.events;

import io.github.teamdevintia.round2.network.internal.CancelableEvent;
import io.netty.channel.Channel;

/**
 * @author Shad0wCore
 */
public class ChannelInitializedEvent extends CancelableEvent {

    private Channel initializedChannel;

    public ChannelInitializedEvent(Channel initializedChannel) {
        this.initializedChannel = initializedChannel;
    }

    public Channel getInitializedChannel() {
        return initializedChannel;
    }
}
