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

public class BanCommand implements CommandExecutor {
    @Command(aliases = {"ban"}, description = "Bans the mentioned user.", usage = "ban <user mentation> [<reason for ban>]")
    public void onMessageCreate(String[] args, Message message, Server server, TextChannel textChannel) {
        User enemy = message.getMentionedUsers().get(0);
        String reasonList = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
        System.out.println(reasonList);
        boolean a = message.getAuthor().canBanUserFromServer(enemy);

        try {
            if (!(message.getAuthor().getDiscriminatedName().equals(enemy.getDiscriminatedName()))) {
                if (a) {
                    if (reasonList.equals("")) {
                        server.banUser(enemy, 1, reasonList);
                    } else {
                        server.banUser(enemy);
                    }
                    EmbedBuilder embed = new CustomEmbedBuilder()
                            .setTitle("Successfully Banned User")
                            .setDescription(enemy.getDiscriminatedName() + " was banned.\nReason: " + reasonList)
                            .setColor(new Color(204, 44, 44));
                    textChannel.sendMessage(embed);
                } else {
                    EmbedBuilder embed = new CustomEmbedBuilder()
                            .setTitle("You don't have permissions")
                            .setDescription("You don't have permissions to ban this user.")
                            .setColor(new Color(204, 44, 44));
                    textChannel.sendMessage(embed);
                }
            } else {
                EmbedBuilder embed = new CustomEmbedBuilder()
                        .setTitle("You're trying to ban yourself.")
                        .setColor(new Color(204, 44, 44));
                textChannel.sendMessage(embed);
            }
        } catch (Exception e) {
            EmbedBuilder embed = new CustomEmbedBuilder()
                    .setTitle("Something happened.")
                    .setDescription("The bot probably doesn't have permissions to ban this user, or the bot failed to ban the user.")
                    .setColor(new Color(204, 44, 44));
            textChannel.sendMessage(embed);
        }
    }
}
