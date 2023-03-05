package com.mateo9x.carbot.event;

import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.handler.MessageHandler;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@AllArgsConstructor
public class MessageListener extends ListenerAdapter {

    private CarBot carBot;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getAuthor().isBot()) {
            return;
        }
        MessageHandler.handleMessage(carBot, event.getMessage());
    }
}
