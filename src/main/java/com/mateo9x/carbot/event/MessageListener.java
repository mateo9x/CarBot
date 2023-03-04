package com.mateo9x.carbot.event;

import com.mateo9x.carbot.handler.MessageHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getAuthor().isBot()) {
            return;
        }
        MessageHandler.handleMessage(event.getMessage());
    }
}
