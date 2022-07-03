package eu.overnetwork.cmd.fun;

import eu.overnetwork.core.Main;
import eu.overnetwork.util.Command;
import eu.overnetwork.util.CommandFunctions;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.Optional;

public class SimpRate implements Command {
    @Override
    public void runCommand(MessageCreateEvent event) {
        int rate = CommandFunctions.randomRate();
            new MessageBuilder().setEmbed(new EmbedBuilder()
                            .setAuthor(event.getMessageAuthor())
                            .setTitle("Simp Calculator")
                            .setDescription(String.format("%s is **%d%%** simp", event.getMessageAuthor().getDisplayName(), rate))
                            .setColor(Color.BLACK))
                    .send(event.getChannel())
                    .exceptionally(exception -> {   // Error message for failing to respond to the guild command
                        Main.logger.error("Unable to respond to the guild command!");
                        Main.logger.error(exception.getMessage());
                        return null;
                    });
    }

    @Override
    public void runCommand(SlashCommandCreateEvent event) {
        SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        Optional<Server> server = slashCommandInteraction.getServer();
        User user = slashCommandInteraction.getUser();
        int rate = CommandFunctions.randomRate();
        server.ifPresent(value -> slashCommandInteraction.createImmediateResponder()
                .addEmbed(new EmbedBuilder()
                        .setAuthor(user)
                        .setTitle("Simp Calculator")
                        .setDescription(String.format("%s is **%d%%** simp", user.getDisplayName(server.get()), rate))
                        .setColor(Color.BLACK))
                .respond()
                .exceptionally(exception -> {   // Error message for failing to respond to the slash command
                    Main.logger.error("Unable to respond to the slash command!");
                    Main.logger.error(exception.getMessage());
                    return null;
                })
        );
    }
}
