package eu.overnetwork.util.database.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class DiscordServer {
    @Id
    private String id;

    @Field
    private String guildPrefix;

    /**
     * The constructor for the repo
     * @param id the Discord server's ID
     * @param guildPrefix the Discord server's guild prefix
     */
    public DiscordServer(String id, String guildPrefix) {
        this.id = id;
        this.guildPrefix = guildPrefix;
    }

    /**
     * Method to get Discord server's OD
     * @return the Discord server's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Method to set Discord server's OD
     * @param id the Discord server's ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method to get server's current guild prefix
     * @return the current discord guild prefix
     */
    public String getGuildPrefix() {
        return guildPrefix;
    }

    public void setGuildPrefix(String prefix) {
        this.guildPrefix = guildPrefix;
    }

     /**
     * Method that overrides the repositories existing toString method
      * @return returns a string with the discord server id and current prefix
     */
     @Override
    public String toString() {
         return String.format("Server[id='%s, guildPrefix='%s", id, guildPrefix);
     }
}
