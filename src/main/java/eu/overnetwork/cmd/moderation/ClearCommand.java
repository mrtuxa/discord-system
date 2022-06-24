package eu.overnetwork.cmd.moderation;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import eu.overnetwork.core.Constant;
import eu.overnetwork.util.CustomEmbedBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ClearCommand implements CommandExecutor {
    @Command(aliases = {"!clear", "!purge"}, description = "\"Clears the number of messages declared from the channel.", usage = "clear <#>")
    public void onMessageCreate(Message message, TextChannel textChannel, String[] args) {
        int messagesDeleted;
        try {
            messagesDeleted = Integer.parseInt(args[0]) + 1;
            textChannel.getMessages(messagesDeleted).get().deleteAll();
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Cleared Messages")
                    .setDescription(args[0] + " messages have been deleted from this text channel." + "\nRequested by: " + message.getAuthor().getDiscriminatedName())
                    .setColor(Constant.JAVACORD_ORANGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Error").setDescription("Something went wrong");
            textChannel.sendMessage(embed);
        }
    }
}
