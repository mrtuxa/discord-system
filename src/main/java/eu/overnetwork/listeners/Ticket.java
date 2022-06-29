package eu.overnetwork.listeners;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Ticket implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Settings cfg = new Settings();
        if (event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "ticket")) {
            //
        }
    }
}
