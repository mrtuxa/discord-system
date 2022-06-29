package eu.overnetwork.additional.music.commands;

import eu.overnetwork.additional.music.audio.AudioManager;
import eu.overnetwork.additional.music.base.ServerCommand;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class LeaveCommand extends ServerCommand {
    public LeaveCommand() {
        super("leave");
    }

    @Override
    protected void runCommand(MessageCreateEvent event, Server server, ServerTextChannel channel, User user, String[] args) {
        server.getConnectedVoiceChannel(event.getApi().getYourself()).ifPresentOrElse(serverVoiceChannel -> {
            server.getAudioConnection().ifPresentOrElse(connection -> {
                AudioManager.get(server.getId()).player.stopTrack();
                connection.close();

            }, () -> event.getChannel().sendMessage("The bot doesn't seem to be in any voice channel."));

        }, () -> event.getChannel().sendMessage("The bot doesn't seem to be in any voice channel."));
    }
}
