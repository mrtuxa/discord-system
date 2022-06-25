package eu.overnetwork.listeners;

import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;

public class ServerVoice implements ServerVoiceChannelMemberLeaveListener {
    @Override
    public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent event) {
        event.getServer().getAudioConnection().ifPresent(audioConnection -> {
            if (audioConnection.getChannel() == event.getChannel()) {
                if (event.getChannel().getConnectedUsers().size() <= 1) {
                    audioConnection.close();
                }
            }
        });
    }
}
