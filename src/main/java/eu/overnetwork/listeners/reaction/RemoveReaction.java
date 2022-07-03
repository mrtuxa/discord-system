package eu.overnetwork.listeners.reaction;

import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

import java.util.HashMap;
import java.util.Map;

public class RemoveReaction implements ReactionRemoveListener {
    private static final String NEWS = "\uD83D\uDCF0";
    private static final String STATUSMELDUNGEN = "\uD83D\uDCC8";
    private static final String VERIFY = "✅";


    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        // role
        Map<String, Long> namesMap = new HashMap<>();
        namesMap.put(VERIFY, 987683142192218143L);
        namesMap.put(NEWS, 987690787074633738L);
        namesMap.put(STATUSMELDUNGEN, 987690891198234645L);

        if (event.getServer().isPresent() && event.getUser().isPresent()) {
        }
    }
}
