package eu.overnetwork.cmd.VerifySystem;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Verify implements MessageCreateListener {
    Settings cfg = new Settings();
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "verify")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Over Network Systems")
                    .addField("***Verifizieren***", "Reagiere mit der Nachricht unten, um dich zu verifizieren!")
                    .setThumbnail("over-hosting_new.png");
        }
    }
}
