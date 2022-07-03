package eu.overnetwork.util;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;

public interface Command {
    void runCommand(MessageCreateEvent event);

    void runCommand(SlashCommandCreateEvent event);
}
