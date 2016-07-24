package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.handlers.WrapperServerNetHandler;

/**
 * @author Shad0wCore
 */
public class Test1  {

    public static void main(String[] args) {

        WrapperServerNetHandler wrapperServerNetHandler = new WrapperServerNetHandler();
        wrapperServerNetHandler.establishServerConnection("127.0.0.1", 8000, streamHandler -> {

        });

    }

}
