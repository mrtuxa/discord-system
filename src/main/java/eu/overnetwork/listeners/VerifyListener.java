package eu.overnetwork.listeners;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class VerifyListener implements MessageCreateListener {
    /**
     * @param event
     */
    private static final String german = ":flag_de:";
    private static final String english = ":flag_us:";
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Settings cfg = new Settings();

       if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "verify")) {
           EmbedBuilder embed = new EmbedBuilder()
                   .setTitle("Over Network Systems")
                   .addField("***Verifizieren***", "Reagiere mit der Nachricht unten, um dich zu Verifizieren!")
                   .setThumbnail("https://raw.githubusercontent.com/mrtuxa/bot-images/main/over-hosting_new.png");
           event.getChannel().sendMessage(embed);
       } else if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "rollen")) {
           EmbedBuilder rollenembed = new EmbedBuilder()
                   .setTitle("Over-Network System")
                   .setAuthor("Over-Network", cfg.getWEBSITE(), cfg.getPROFILE())
                   .setThumbnail(cfg.getPROFILE())
                   .setColor(Color.BLACK)
                   .addField("Rollenvergabe", "Reagiere mit folgende Emojis, um die Ränge freizuschalten")
                   .addField(":newspaper:", "News")
                   .addField(":chart_with_upwards_trend:", "Statusmeldungen");
       }
    }
}
