package eu.overnetwork.core;

import eu.overnetwork.handler.cmd.CommandHandler;
import eu.overnetwork.util.CommandRegister;
import eu.overnetwork.util.database.models.DiscordServer;
import eu.overnetwork.util.database.repositories.DiscordServerRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

public class Main {
    public static String defaultGuildPrefix = "!";

    public static String[] audioSources = {
            "youtube",
    };

    public static String youtubeApiKey = Dotenv.load().get("YoutubeApiKey");

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static DiscordServerRepository discordServerRepository = null;

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

    @Autowired
    private CommandHandler commandHandler;

    public Main(DiscordServerRepository discordServerRepo) {
        discordServerRepository = discordServerRepo;
    }

    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {
        String botToken = Dotenv.load().get("TOKEN");
        DiscordApi api = new DiscordApiBuilder()
                .setToken(botToken)
                .setAllIntents()
                .setWaitForUsersOnStartup(true)
                .setWaitForServersOnStartup(true)
                .login().exceptionally(exception -> {
                    logger.error("Error setting up DiscordApi instance");
                    return null;
                })
                .join();

        logger.info("Bot has been started");

        // Handling server entries in the database
        if (discordServerRepository.findAll().isEmpty()) {
           logger.trace("Bot server data repository empty, initializing data repository...");
            Collection<Server> servers = api.getServers();
            for (Server server : servers) {
                discordServerRepository.save(new DiscordServer(String.valueOf(server.getId()), defaultGuildPrefix));
            }
            logger.trace("Bot server data repository initialized");
        }

        CommandRegister.run(api, commandHandler);
        return api;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
