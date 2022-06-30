package eu.overnetwork.cmd;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class Verify implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Settings cfg = new Settings();

        if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "verify")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Over Network Systems")
                    .addField("***Verifizieren***", "Reagiere mit der Nachricht unten, um dich zu verifizieren!")
                    .setThumbnail("https://raw.githubusercontent.com/mrtuxa/bot-images/main/over-hosting_new.png");
            event.getChannel().sendMessage(embed);
        } else if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "rollen")) {
            EmbedBuilder roleEmbed = new EmbedBuilder()
                    .setTitle("Over-Network System")
                    .setAuthor("Over-Network", cfg.getWebsite(), cfg.getProfile())
                    .setThumbnail(cfg.getProfile())
                    .setColor(Color.BLACK)
                    .addField("Rollenvergabe", "Reagiere mit folgende Emojis, um die Ränge freizuschalten!")
                    .addField(":newspaper:", "News")
                    .addField(":chart_with_upwards_trend:", "Statusmeldungen");
        }
    }
}
