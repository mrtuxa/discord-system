package eu.overnetwork.cmd.utils;

import eu.overnetwork.util.Command;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.Optional;

public class PingCommand implements Command {

    @Override
    public void runCommand(MessageCreateEvent event) {
        new MessageBuilder().setEmbed(new EmbedBuilder()
                        .setTitle(String.format("Hello %s!", event.getMessageAuthor().getDisplayName()))
                        .setThumbnail(event.getMessageAuthor().getAvatar())
                        .setColor(Color.BLACK))
                .send(event.getChannel())
                .exceptionally(exception -> {   // Error message for failing to respond to the guild command
                    System.out.println("Unable to respond to the guild command!");
                    return null;
                });
    }

    @Override
    public void runCommand(SlashCommandCreateEvent event) {
        SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        Optional<Server> server = slashCommandInteraction.getServer();
        server.ifPresent(value -> slashCommandInteraction.createImmediateResponder()
                .addEmbed(new EmbedBuilder()
                        .setTitle(String.format("Hello %s!", slashCommandInteraction.getUser().getDisplayName(server.get())))
                        .setThumbnail(slashCommandInteraction.getUser().getAvatar())
                        .setColor(Color.BLACK))
                .respond()
                .exceptionally(exception -> {   // Error message for failing to respond to the slash command interaction
                    System.out.println("Unable to respond to the slash command interaction");
                    return null;
                })
        );
    }
}
