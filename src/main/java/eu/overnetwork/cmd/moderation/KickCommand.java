package eu.overnetwork.cmd.moderation;

import com.sun.xml.bind.v2.runtime.reflect.opt.Const;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import eu.overnetwork.core.Constant;
import eu.overnetwork.util.CustomEmbedBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.util.Arrays;

public class KickCommand implements CommandExecutor {

    @Command(aliases = {"kick"}, description = "Kicks the mentioned user.", usage = "kick <user mention> [<reason for kick>]")
    public void onMessageCreate(Message message, TextChannel channel, Server server, String[] args) {
        User userToKick = message.getMentionedUsers().get(0);
        System.out.println(userToKick.getDiscriminatedName());
        String reasonList = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        if (reasonList.equals("")) reasonList = "No reason was provided by " + message.getAuthor().getDiscriminatedName() + ".";
        System.out.println(reasonList);
        boolean a = message.getAuthor().canKickUserFromServer(userToKick);
        try {
            if (!(message.getAuthor().getDiscriminatedName().equals(userToKick.getDiscriminatedName()))) {
                if (a) {
                    if (args.length > 2) {
                        server.kickUser(userToKick, reasonList);
                    } else {
                        server.kickUser(userToKick);
                    }
                    EmbedBuilder embed = new CustomEmbedBuilder()
                            .setTitle("Successfully Kicked User")
                            .setDescription(userToKick.getName() + "#" + userToKick.getDiscriminator() + " was kicked.\nReason: " + reasonList)
                            .setColor(new Color(204, 44, 44));
                    channel.sendMessage(embed);
                } else {
                    EmbedBuilder embed = new CustomEmbedBuilder()
                            .setTitle("You don't have permissions")
                            .setDescription("You don't have permissions to kick this user.")
                            .setColor(new Color(204, 44, 44));
                    channel.sendMessage(embed);
                }
            } else {
                EmbedBuilder embed = new CustomEmbedBuilder()
                        .setTitle("You're trying to kick yourself.")
                        .setColor(new Color(204, 44, 44));
                channel.sendMessage(embed);
            }
        } catch (Exception e) {
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot probably doesn't have permissions to kick this user, or the bot failed to kick the user.")
                    .setColor(new Color(204, 44, 44));
            channel.sendMessage(embed);
        }
    }
}
