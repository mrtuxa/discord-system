package eu.overnetwork.util;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.jetbrains.annotations.NotNull;

public class RemoveRole {

    public RemoveRole(@NotNull Server server, User user, long roleId) {
        server.getRoleById(roleId).ifPresent(role -> role.removeUser(user));
    }
}
