package eu.overnetwork.reaction;

import eu.overnetwork.util.NamesMap;
import eu.overnetwork.util.RemoveRole;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.jetbrains.annotations.NotNull;

import javax.naming.Name;
import java.awt.*;
import java.io.File;

public class VerifyRemove implements ReactionRemoveListener {
    private static final String VERIFY = "✅";

    /**
     * This method is called every time a reaction is removed from a message.
     *
     * @param event The event.
     */
    @Override
    public void onReactionRemove(@NotNull ReactionRemoveEvent event) {
        if (event.getServer().isPresent() && event.getUser().isPresent()) {
            Server server = event.getServer().get();
            User user = event.getUser().get();
            String emoji = event.getEmoji().asUnicodeEmoji().orElse("");

            if (VERIFY.equals(emoji)) {
                new RemoveRole(server, user, new NamesMap().get("VERIFY"));
                EmbedBuilder removeRole = new EmbedBuilder()
                        .setTitle("Over-Network System")
                        .setThumbnail(new File("https://raw.githubusercontent.com/mrtuxa/bot-images/main/over-hosting_new.png"))
                        .addField("Verify System", "You've unverified yourself")
                        .setColor(Color.BLACK)
                        .setFooter("over-network");
                user.sendMessage(removeRole);
            }
        }
    }
}
