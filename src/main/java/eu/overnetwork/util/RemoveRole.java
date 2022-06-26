package eu.overnetwork.util;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class RemoveRole {

    private static Server server;
    private static User user;
    private static Long roleID;

    public RemoveRole(Server server, User user, Long news) {
        server.getRoleById(roleID).ifPresent(role -> role.removeUser(user));
    }
}
