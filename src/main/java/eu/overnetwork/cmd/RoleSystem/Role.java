package eu.overnetwork.cmd.RoleSystem;

import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;

public class Role implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        EmbedBuilder roleEmbed = new EmbedBuilder()
                .setTitle("Over-Network System")
                .setAuthor("Over-Network", "https://over-network.eu", new File("over-hosting_new.png"))
                .setThumbnail(new File("over-hosting_new.png"))
                .setColor(Color.BLACK)
                .addField("Rollenvergabe", "Reagiere mit folgende Emojis, um die Ränge freizuschalten!\n " + EmojiParser.parseToUnicode(":newspaper:") + " News\n " + EmojiParser.parseToUnicode(":chart_with_upwards_trend:") + " Statusmeldungen");
        event.getChannel().sendMessage(roleEmbed);
    }
}
