package eu.overnetwork.core;

import eu.overnetwork.cmd.utils.PingCommand;
import eu.overnetwork.handler.cmd.CommandHandler;
import eu.overnetwork.listeners.music.JoinVoiceListener;
import eu.overnetwork.listeners.reaction.AddReaction;
import eu.overnetwork.listeners.reaction.RemoveReaction;
import eu.overnetwork.util.CommandRegister;
import eu.overnetwork.util.database.models.DiscordServer;
import eu.overnetwork.util.database.repositories.DiscordServerRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

public class Main {

    public enum VoiceConnectionStatus {
        /**
         * State for successful audio connection
         */
        Successful,
        /**
         * State for unsuccessful audio connection
         */
        Unsuccessful,
        /**
         * State for a pre-existing audio connection
         */
        AlreadyConnected
    }

    public static String defaultGuildPrefix = "!";

    public static String[] audioSources = {
            "youtube",
    };

    @Autowired
    private static CommandHandler commandHandler;
    public static DiscordServerRepository discordServerRepository = null;

    public Main(DiscordServerRepository discordServerRepo) {
        discordServerRepository = discordServerRepo;
    }

    public static void main(String[] args) {
        String botToken = Dotenv.load().get("TOKEN");

        DiscordApi api = new DiscordApiBuilder()
                .setToken(botToken)
                .setAllIntents()
                .setWaitForServersOnStartup(true)
                .setWaitForUsersOnStartup(true)
                .login()
                .join();
        System.out.println("Bot has been started");

        // handling server entries in database
       /* if (discordServerRepository.findAll().isEmpty()) {
            System.out.println("Bot server data repository empty, initializing data repository...");
            Collection<Server> servers = api.getServers();
            for (Server server : servers) {
                discordServerRepository.save(new DiscordServer(String.valueOf(server.getId()), defaultGuildPrefix));
            }
            System.out.println("Bot server data repo initialized");*/

        CommandRegister.run(api, commandHandler);


        api.addReactionAddListener(new AddReaction());
        api.addReactionRemoveListener(new RemoveReaction());
        api.addListener(new JoinVoiceListener());
        System.out.println("Connected as " + api.getYourself().getDiscriminatedName());

    }
}

