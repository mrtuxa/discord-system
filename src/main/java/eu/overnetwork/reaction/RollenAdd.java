package eu.overnetwork.reaction;

import com.vdurmont.emoji.EmojiParser;
import eu.overnetwork.util.AssingRole;
import eu.overnetwork.util.NamesMap;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import java.io.File;


public class RollenAdd implements ReactionAddListener {
    private  static final String VERIFY = "✅";
    private static final String NEWS = "\uD83D\uDCF0";
    private static final String STATUSMELDUNGEN = "\uD83D\uDCC8";

    private static final String german = ":flag_de:";
    private static final String english = ":flag_us:";
    /**
     * This method is called every time a reaction is added to a message.
     *
     * @param event The event.
     */

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        Message verify = event.getApi().getMessageById(989862710630047845L, event.getApi().getTextChannelById(989851509686693908L).orElse(null)).join();
        Message Rollen = event.getApi().getMessageById(988135594184024115L, event.getApi().getTextChannelById(987700778607181864L).orElse(null)).join();
        verify.pin();
        verify.addReaction("✅");
        Rollen.addReaction(EmojiParser.parseToUnicode(":newspaper:"));
        Rollen.addReaction(EmojiParser.parseToUnicode(":chart_with_upwards_trend:"));

        if (event.getServer().isPresent() && event.getServer().isPresent()) {
            Server server = event.getServer().get();
            User user = event.getUser().get();
            String emoji = event.getEmoji().asUnicodeEmoji().orElse("");


            switch (emoji) {

                case NEWS:
                    new AssingRole(server, user, NamesMap.namesMap.get("NEWS"));
                    user.sendMessage(new EmbedBuilder()
                            .setTitle("Over-Network System")
                            .setThumbnail("https://raw.githubusercontent.com/mrtuxa/bot-images/main/over-hosting_new.png")
                            .addField("Rollenvergabe", "You've added yourself to the Statusmeldungen Ping Role")
                            .setFooter("Over-Network"));
                    break;
                case STATUSMELDUNGEN:
                    new AssingRole(server, user, NamesMap.namesMap.get("STATUSMELDUNGEN"));
                    user.sendMessage(new EmbedBuilder()
                            .setTitle("Over-Network System")
                            .setThumbnail(new File("https://raw.githubusercontent.com/mrtuxa/bot-images/main/over-hosting_new.png"))
                            .addField("Rollenvergabe", "You've added yourself to the Statusmeldungen Ping Role")
                            .setFooter("Over-Network"));
            }

        }
    }
}
