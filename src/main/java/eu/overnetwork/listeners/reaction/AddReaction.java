package eu.overnetwork.listeners.reaction;

import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import java.util.HashMap;
import java.util.Map;

public class AddReaction implements ReactionAddListener {
    private static final String NEWS = "\uD83D\uDCF0";
    private static final String STATUSMELDUNGEN = "\uD83D\uDCC8";
    private  static final String VERIFY = "✅";

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        Message verifyMessage = event.getApi().getMessageById(989862710630047845L, event.getChannel().getApi().getTextChannelById(989851509686693908L).orElse(null)).join();
        Message roleMessage = event.getApi().getMessageById(988135594184024115L, event.getApi().getTextChannelById(987700778607181864L).orElse(null)).join();

        // role
        Map<String, Long> namesMap = new HashMap<>();
        namesMap.put(VERIFY, 987683142192218143L);
        namesMap.put(NEWS, 987690787074633738L);
        namesMap.put(STATUSMELDUNGEN, 987690891198234645L);

        // verify message
        verifyMessage.pin();
        verifyMessage.addReaction(EmojiParser.parseToUnicode(":white_check_mark:"));

        // role message
        roleMessage.pin();
        roleMessage.addReaction(EmojiParser.parseToUnicode(":newspaper:"));
        roleMessage.addReaction(EmojiParser.parseToUnicode(":chart_with_upwards_trends"));

        if (event.getServer().isPresent() && event.getUser().isPresent()) {
            Server server = event.getServer().get();
            User user = event.getUser().orElse(null);
            String emoji = event.getEmoji().asUnicodeEmoji().orElse("");

            switch (emoji) {
                case NEWS:
                    removeRole(server, user, namesMap.get(NEWS));
                    break;
                case STATUSMELDUNGEN:
                    removeRole(server, user, namesMap.get(STATUSMELDUNGEN));
                    break;
                case VERIFY:
                    removeRole(server, user, namesMap.get(VERIFY));
                    break;
            }
        }
    }

    public static void removeRole(Server server, User user, Long roleID) {
        server.getRoleById(roleID).ifPresent(role -> {
            server.removeRoleFromUser(user, role);
        });
    }
}
