package eu.overnetwork.util;

import eu.overnetwork.core.Main;
import eu.overnetwork.util.database.models.DiscordServer;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;

import java.util.List;
import java.util.Optional;

public class ServerHelperFunctions {
    /**
     * Method that resolves a Javacord Server entity into its corresponding database model
     * @param server the Javacord Server entity object
     * @return returns as DiscordServer database model
     */
    public static DiscordServer resolveServerModelById(Server server){
        DiscordServer discordServer = null;
        Optional<DiscordServer> serverModel = Main.discordServerRepository.findById(String.valueOf(server.getId()));
        if (serverModel.isPresent()) {
           System.out.println("Server model present, getting server model...");
            discordServer = serverModel.get();
        } else {
           System.out.println("Server model not present, returning null...");
        }
        return discordServer;
    }

    /**
     * Method to remove a global slash command from the bot
     * @param api Discord bot api object
     * @param commandName String of the command to be deleted
     */
    public static void removeGlobalSlashCommand(DiscordApi api, String commandName){
        System.out.println(String.format("Request to remove a global slash command by the name '%s' received", commandName));
        boolean commandFound = false;
        List<SlashCommand> slashCommands = api
                .getGlobalSlashCommands()
                .exceptionally(exception -> {   // Error message for failing to get the list of global slash commands
                    System.out.println("Unable to retrieve list of global slash commands!");
                    return null;
                })
                .join();
        for (SlashCommand slashCommand: slashCommands){
            if(slashCommand.getName().equals(commandName)){
                commandFound = true;
                long commandId = slashCommand.getId();
                SlashCommand commandToBeRemoved = api
                        .getGlobalSlashCommandById(commandId)
                        .exceptionally(exception -> {   // Error message for failing to get global slash command by command id
                            System.out.println("Unable to retrieve global slash command by id!");
                            return null;
                        })
                        .join();
                commandToBeRemoved
                        .deleteGlobal()
                        .exceptionally(exception -> {   // Error message for failing to delete command that needed to be removed
                            System.out.println("Unable to delete the requested global slash command!");
                            return null;
                        })
                        .join();
                break;
            }
        }
        if (commandFound){
            System.out.println("The requested command was successfully found and deleted");
        }
        else{
            System.out.println("The requested command was NOT found");
        }
    }
}
