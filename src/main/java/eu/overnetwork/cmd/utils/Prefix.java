package eu.overnetwork.cmd.utils;

import eu.overnetwork.core.Main;
import eu.overnetwork.util.Command;
import eu.overnetwork.util.database.models.DiscordServer;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.awt.*;

import static eu.overnetwork.util.ServerHelperFunctions.resolveServerModelById;

public class Prefix implements Command {

    private EmbedBuilder commandFunction(User user, Server server, String validatedPrefix){
        EmbedBuilder response;
        if (!user.isBot() && server.isAdmin(user)) {
            DiscordServer serverModel = resolveServerModelById(server);
            serverModel.setGuildPrefix(validatedPrefix);
            Main.discordServerRepository.save(serverModel);
            response = new EmbedBuilder()
                    .setTitle("Guild Command Prefix Changed!")
                    .setDescription(String.format("Guild prefix has been set to **%s**", validatedPrefix))
                    .setColor(Color.BLACK);
        } else {
            response = new EmbedBuilder()
                    .setTitle("You cannot change guild command prefixes for this server!")
                    .setDescription("You do not have the required permissions for this action! Contact a server admin and request for a change")
                    .setColor(Color.BLACK);
        }

        return response;
    }

    @Override
    public void runCommand(MessageCreateEvent event) {
        User author;
        Server server;
        MessageAuthor messageAuthor = event.getMessageAuthor();
        String message = event.getMessageContent();
        if (messageAuthor.asUser().isPresent()) {
            author = messageAuthor.asUser().get();
        } else {
            // throw an error and log it.
            throw new NullPointerException("Message author was null");
        }
        if (event.getServer().isPresent()){
            server = event.getServer().get();
        } else {
            // throw an error and log it.
            throw new NullPointerException("Server was null");
        }

        DiscordServer serverModel = resolveServerModelById(server);
        String currentPrefix = serverModel.getGuildPrefix();

        String unvalidatedNewPrefix = message.substring((currentPrefix.length())+("prefix".length())).strip();

        boolean prefixIsValid = false;
        if (unvalidatedNewPrefix.length() != 0){
            prefixIsValid = ((unvalidatedNewPrefix.charAt(0)=='\"') && (unvalidatedNewPrefix.charAt(unvalidatedNewPrefix.length()-1)=='\"'));
        }

        if (!prefixIsValid) {
            new MessageBuilder().setEmbed(
                            new EmbedBuilder()
                                    .setTitle("Prefix Command")
                                    .setDescription(String.format("To use the prefix command, type **%sprefix \"<NEW_PREFIX>\"**\nNote that the new prefix cannot be a blank string or one with only white spaces", currentPrefix))
                                    .setColor(Color.BLACK))
                    .send(event.getChannel())
                    .exceptionally(exception -> {   // Error message for failing to respond to the guild command
                        System.out.println("Unable to respond to the guild command!");
                        return null;
                    });
        } else{
            String newPrefix = unvalidatedNewPrefix.replaceAll("\"", "");
            if (newPrefix.isBlank()) {
                new MessageBuilder().setEmbed(
                                new EmbedBuilder()
                                        .setTitle("Blank text cannot be set as a prefix!")
                                        .setDescription("Make sure to set a prefix that is not blank by following the correct syntax")
                                        .setColor(Color.BLACK))
                        .send(event.getChannel())
                        .exceptionally(exception -> {   // Error message for failing to respond to the guild command
                            System.out.println("Unable to respond to the guild command!");
                            return null;
                        });
            } else {
                EmbedBuilder response = commandFunction(author, server, newPrefix);
                new MessageBuilder().setEmbed(response)
                        .send(event.getChannel())
                        .exceptionally(exception -> { // Error message for failing to respond to the guild command
                            System.out.println("Unable to respond to the guild command!");
                            return null;
                        });
            }
        }
    }

    @Override
    public void runCommand(SlashCommandCreateEvent event) {
        SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        User author = slashCommandInteraction.getUser();
        slashCommandInteraction.getServer()
                .ifPresent(server -> slashCommandInteraction.getOptionByName("prefix-string").flatMap(SlashCommandInteractionOption::getStringValue)
                        .ifPresent(newPrefix -> {
                            EmbedBuilder response = commandFunction(author, server, newPrefix);
                            slashCommandInteraction.createImmediateResponder().addEmbed(response)
                                    .respond()
                                    .exceptionally(exception -> {   // Error message for failing to respond to the slash command interaction
                                        System.out.println("Unable to respond to the slash command interaction");
                                        return null;
                                    });
                        }));
    }
}
