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
import org.testng.internal.collections.Pair;

import java.awt.*;
import java.util.Optional;

public class Gayrate implements Command {
    @Override
    public void runCommand(MessageCreateEvent event) {
        Pair<String, Integer> gaynessAndRate = CommandFunctions.gayRate();
        String gayness = gaynessAndRate.first();
        int rate = gaynessAndRate.second();
        new MessageBuilder().setEmbed(new EmbedBuilder()
                .setAuthor(event.getMessageAuthor())
                .setTitle("Gay Calculator")
                .setDescription(String.format("%s is **%d%%** gay", event.getMessageAuthor().getDisplayName(), rate))
                .setThumbnail(gayness)
                .setColor(Color.BLACK))
                .send(event.getChannel());
    }

    @Override
    public void runCommand(SlashCommandCreateEvent event) {
        SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        Optional<Server> server = slashCommandInteraction.getServer();
        User user = slashCommandInteraction.getUser();
        Pair<String, Integer> gaynessAndRate = CommandFunctions.gayRate();
        String gayness = gaynessAndRate.first();
        int rate = gaynessAndRate.second();
        server.ifPresent(value -> slashCommandInteraction.createImmediateResponder()
                .addEmbed(new EmbedBuilder()
                        .setAuthor(user)
                        .setDescription(String.format("%s is **%d%%** gay", user.getDisplayName(server.get()), rate))
                        .setThumbnail(gayness)
                        .setColor(Color.BLACK))
                .respond()
                .exceptionally(exception -> {
                    Main.logger.error("Unable to respont to the slash command interaction");
                    Main.logger.error(exception.getMessage());
                    return null;
                })
        );
    }
}
