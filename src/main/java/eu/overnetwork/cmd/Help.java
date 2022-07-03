package eu.overnetwork.cmd;

import eu.overnetwork.core.Main;
import eu.overnetwork.handler.cmd.CommandHandler;
import eu.overnetwork.util.Command;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.io.File;

import static eu.overnetwork.util.ServerHelperFunctions.resolveServerModelById;

public class Help implements Command {

    @Override
    public void runCommand(MessageCreateEvent event) {
        event.getServer().ifPresent(server -> {
            String commandPrefix = resolveServerModelById(server).getGuildPrefix();
            new MessageBuilder().setEmbed(new EmbedBuilder()
                            .setTitle("List of guild commands and their descriptions")
                            .setDescription(CommandHandler.generateHelpDescription(commandPrefix))
                            .setThumbnail(new File("over-hosting_new.png"))
                            .setColor(Color.BLACK))
                    .send(event.getChannel())
                    .exceptionally(exception -> {   // Error message for failing to respond to the guild command
                        System.out.println("Unable to respond to the guild command!");
                        return null;
                    });
        });
    }

    @Override
    public void runCommand(SlashCommandCreateEvent event) {
        SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        slashCommandInteraction.getServer().ifPresent(server -> slashCommandInteraction.createImmediateResponder()
                .addEmbed(new EmbedBuilder()
                        .setTitle("List of slash commands and their descriptions")
                        .setDescription(CommandHandler.generateHelpDescription("/")) // Slash commands don't actually have command prefixes
                        .setThumbnail(new File("over-hosting_new.png"))
                        .setColor(Color.BLACK)
                ).respond()
                .exceptionally(exception -> {   // Error message for failing to respond to the slash command interaction
                    System.out.println("Unable to respond to the slash command interaction");
                    return null;
                })
        );
    }
}
