package eu.overnetwork.listeners;

import eu.overnetwork.util.CustomEmbedBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ServerJoin implements ServerMemberJoinListener {
    /**
     * This method is called every time a user joins a server.
     *
     * @param event The event.
     *
     */
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {
        Optional<TextChannel> channel = event.getApi().getTextChannelById(987700321155448872L);
       /* channel.ifPresent(textChannel -> textChannel.sendMessage(*/
        EmbedBuilder c2 = new CustomEmbedBuilder()
                .setTitle("Loading user from database :hourglass: ");

        EmbedBuilder c1 = new EmbedBuilder()
                .setTitle("Over-Network System")
                .setAuthor("Over-Network", "https://over-network.eu", "https://raw.githubusercontent.com/mrtuxa/bot-images/main/over-hosting_new.png")
                .setThumbnail(event.getUser().getAvatar())
                .addField("Willkommen auf Over-Network", event.getUser().getMentionTag())
                .addField("Verifikation", "<#989851509686693908>")
                .addField("Community Chat", "<#987683158097006622>")
                .setFooter("by Over-Network");
        event.getServer().getTextChannelById(987700321155448872L).ifPresent(serverTextChannel -> serverTextChannel.sendMessage(c2).thenAccept(MessageToBeEdited -> MessageToBeEdited.getApi().getThreadPool().getScheduler().schedule(() -> {
            MessageToBeEdited.edit(c1);
        }, 2, TimeUnit.SECONDS)));

    }
}
