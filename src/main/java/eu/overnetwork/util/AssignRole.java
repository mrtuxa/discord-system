package eu.overnetwork.util;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.jetbrains.annotations.NotNull;

public class AssignRole {

    public AssignRole(@NotNull Server server, User user, long role) {
        server.getRoleById(role).ifPresent(r -> r.addUser(user));
    }
}
