package eu.overnetwork.core;

import eu.overnetwork.cmd.Tags;
import eu.overnetwork.listeners.*;
import eu.overnetwork.cfg.Settings;
import com.vdurmont.emoji.EmojiParser;
import eu.overnetwork.music.audio.PlayerManager;
import eu.overnetwork.music.commands.LeaveCommand;
import eu.overnetwork.music.commands.PlayCommand;
import eu.overnetwork.music.commands.SkipCommand;
import eu.overnetwork.music.commands.StopCommand;
import eu.overnetwork.util.LatestVersionFinder;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Main {


    private  static final String VERIFY = "✅";
    private static final String NEWS = "\uD83D\uDCF0";
    private static final String STATUSMELDUNGEN = "\uD83D\uDCC8";

    private static final String german = ":flag_de:";
    private static final String english = ":flag_us:";
    public static void main(String[] args) {

        PlayerManager.init();

        Settings cfg = new Settings();
        Dotenv dotenv = Dotenv.load();
        DiscordApi api = new DiscordApiBuilder()
                .setToken(dotenv.get("TOKEN"))
                .setAllIntents()
                .login()
                .join();


        System.out.println("Connected with " + api.getYourself().getDiscriminatedName());

        api.addServerVoiceChannelMemberLeaveListener(new ServerVoice());

        // Tool for finding the latest version.
        LatestVersionFinder versionFinder = new LatestVersionFinder(api);


        api.addMessageDeleteListener(new CommandCleanupListener());
        api.addMessageCreateListener(new VerifyListener());
        api.addListener(new Ping());
        api.addListener(new Rollen());
        api.addListener(new Language());
        api.addListener(new PlayCommand());
        api.addListener(new SkipCommand());
        api.addListener(new StopCommand());
        api.addListener(new LeaveCommand());
        api.addListener(new Tags());
        api.addServerMemberJoinListener(new ServerJoin());


        Map<String, Long> namesMap = new HashMap<>();
        namesMap.put(VERIFY, 987683142192218143L);
        namesMap.put(NEWS, 987690787074633738L);
        namesMap.put(STATUSMELDUNGEN, 987690891198234645L);
        // namesMap.put(german, )

        // Verify Role message
          Message verify = api.getMessageById(989862710630047845L, api.getTextChannelById(989851509686693908L).get()).join();
        //  Message ready = api.getMessageById(987690631788892190L, api.getTextChannelById(987689802394660865L).get()).join();
         Message Rollen = api.getMessageById(988135594184024115L, api.getTextChannelById(987700778607181864L).get()).join();

         // ready.addReaction("✅");
         verify.pin();

         verify.addReaction("✅");


         Rollen.pin();
         Rollen.addReaction(EmojiParser.parseToUnicode(":newspaper:"));
         Rollen.addReaction(EmojiParser.parseToUnicode(":chart_with_upwards_trend:"));

        Rollen.addReactionAddListener(reactionAddEvent -> {
            if (reactionAddEvent.getServer().isPresent() && reactionAddEvent.getServer().isPresent()) {
                Server server = reactionAddEvent.getServer().get();
                User user = reactionAddEvent.getUser().get();
                String emoji = reactionAddEvent.getEmoji().asUnicodeEmoji().orElse("");

                switch (emoji) {
                    case NEWS:
                        assignRole(server, user, namesMap.get(NEWS));
                        user.sendMessage(new EmbedBuilder()
                                .setTitle("Over-Network System")
                                .setThumbnail(cfg.getPROFILE())
                                .addField("Rollenvergabe", "You've added yourself to the Statusmeldungen Ping Role")
                                .setFooter("Over-Network"));
                        break;
                    case STATUSMELDUNGEN:
                        assignRole(server, user, namesMap.get(STATUSMELDUNGEN));
                        user.sendMessage(new EmbedBuilder()
                                .setTitle("Over-Network System")
                                .setThumbnail(cfg.getPROFILE())
                                .addField("Rollenvergabe", "You've added yourself to the Statusmeldungen Ping Role")
                                .setFooter("Over-Network"));
                }
            }
        });

        Rollen.addReactionRemoveListener(reactionRemoveEvent -> {
            if (reactionRemoveEvent.getServer().isPresent() && reactionRemoveEvent.getServer().isPresent()) {
                Server server = reactionRemoveEvent.getServer().get();
                User user = reactionRemoveEvent.getUser().get();
                String emoji = reactionRemoveEvent.getEmoji().asUnicodeEmoji().orElse("");

                switch (emoji) {
                    case NEWS:
                        removeRole(server, user, namesMap.get(NEWS));
                        user.sendMessage(new EmbedBuilder()
                                .setTitle("Over-Network System")
                                .setThumbnail(cfg.getPROFILE())
                                .addField("Rollenvergabe", "You've removed yourself from the News Ping Role")
                                .setFooter("Over-Network")
                        );
                        break;
                    case STATUSMELDUNGEN:
                        removeRole(server, user, namesMap.get(STATUSMELDUNGEN));
                        user.sendMessage(new EmbedBuilder()
                                .setTitle("Over-Network System")
                                .setThumbnail(cfg.getPROFILE())
                                .addField("Rollenvergabe", "You've removed yourself to the Statusmeldungen Ping Role")
                                .setFooter("Over-Network"));
                        break;
                }
            }
        });

        verify.addReactionAddListener(event -> {
            if (event.getServer().isPresent() && event.getUser().isPresent()) {
                Server server = event.getServer().get();
                User user = event.getUser().get();
                String emoji = event.getEmoji().asUnicodeEmoji().orElse("");

                switch (emoji) {
                    case VERIFY:
                        assignRole(server, user, namesMap.get(VERIFY));

                        EmbedBuilder addrole = new EmbedBuilder()
                                .setTitle("Over-Network System")
                                .setThumbnail(cfg.getPROFILE())
                                .addField("Verify System", "You've verified yourself")
                                .setColor(Color.BLACK)
                                .setFooter("over-network");
                        user.sendMessage(addrole);
                }
            }
        });

        verify.addReactionRemoveListener(event -> {
            if (event.getServer().isPresent() && event.getUser().isPresent()) {
                Server server = event.getServer().get();
                User user = event.getUser().get();
                String emoji = event.getEmoji().asUnicodeEmoji().orElse("");

                switch (emoji) {
                    case VERIFY:
                        removeRole(server, user, namesMap.get(VERIFY));
                        EmbedBuilder removerole = new EmbedBuilder()
                                .setTitle("Over-Network System")
                                .setThumbnail(cfg.getPROFILE())
                                .addField("Verify System", "You've unverified yourself")
                                .setColor(Color.BLACK)
                                .setFooter("over-network");
                        user.sendMessage(removerole);
                        break;

                }

            }
        });


        api.addMessageComponentCreateListener(messageComponentCreateEvent -> {
            MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();
            String customId = messageComponentInteraction.getCustomId();

            switch (customId) {
                case "german":
                    messageComponentInteraction.createImmediateResponder()
                            .setContent("You selected German")
                            .respond();
                    //      messageComponentInteraction.getUser().addRole(german, )
            }
        });
    }
    private int[] generateEveryoneNumbers() {
        int[] numbers = {};
        Random r = new Random();

        for (int i = 0; i < 9; i++) {
            numbers[i] = r.nextInt(1) + 1;
        }
        return numbers;
    }

    private static void getActivityInfo(String[] playing, DiscordApi api, User user) {
        api.getActivity().ifPresent(activity -> {
            if (activity.getName().equalsIgnoreCase("Spotify")) {
                playing[0] = "Listening to " + activity.getDetails().get();
            } else {
                playing[0] = "Playing " + activity.getName();
            }
        });

        if (!api.getActivity().isPresent()) {
            playing[0] = "Currently " + api.getStatus().getStatusString();
        }
    }


    private static void assignRole(Server server, User user, Long roleID) {
        server.getRoleById(roleID).ifPresent(role -> role.addUser(user));
    }

    private static void removeRole(Server server, User user, Long roleID) {
        server.getRoleById(roleID).ifPresent(role -> role.removeUser(user));
    }

}
