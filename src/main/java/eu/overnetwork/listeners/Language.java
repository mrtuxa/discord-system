package eu.overnetwork.listeners;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Language implements MessageCreateListener {
    /**
     * @param event
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Settings cfg = new Settings();
        if (event.getMessageAuthor().canBanUsersFromServer() && event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "language")) {
           TextChannel channel = event.getMessage().getChannel();
            EmbedBuilder language = new EmbedBuilder()
                    .setTitle("Over-Network System")
                    .addField("Support", "Please choose your language for the support")
                    .setThumbnail(cfg.getPROFILE())
                    .setFooter("Over-Network System");

            event.getChannel().sendMessage(language);


            TextChannel buttonchannel = event.getChannel();
            new MessageBuilder()
                    .addComponents(
                            ActionRow.of(Button.success("german", "German"),
                                    Button.danger("english", "English")))
                    .send(buttonchannel);
        }
    }
}
