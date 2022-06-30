package eu.overnetwork.additional.music.base;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public abstract class ServerCommand implements MessageCreateListener {

    private final String command;
    private final String prefix = "!";

    protected ServerCommand(String command) {
        this.command = command;
    }


    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if (!messageCreateEvent.isServerMessage())
            return;
        if (!messageCreateEvent.getMessageAuthor().isRegularUser())
            return;
        if (!messageCreateEvent.getMessageContent().startsWith(prefix + command))
            return;

        //run everything
        messageCreateEvent.getServer().ifPresent(server -> messageCreateEvent.getMessageAuthor().asUser().ifPresent(user ->
                messageCreateEvent.getServerTextChannel().ifPresent(serverTextChannel -> runCommand(messageCreateEvent, server, serverTextChannel, user, messageCreateEvent.getMessageContent().split(" ")))));
    }

    protected abstract void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args);
}

