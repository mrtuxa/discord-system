package eu.overnetwork.music.commands;

import eu.overnetwork.music.audio.AudioManager;
import eu.overnetwork.music.base.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class StopCommand extends ServerCommand {

    public StopCommand() {
        super("stop");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {
        // Checks if there are any audio connection.
        server.getAudioConnection().ifPresentOrElse(connection -> {

            // If there is an audio connection then we stop the track.
            AudioManager.get(server.getId()).scheduler.nextTrack();
            event.getChannel().sendMessage("We have stopped the tracked");

        }, () -> event.getChannel().sendMessage("The bot doesn't seem to be playing any music."));
    }
}
