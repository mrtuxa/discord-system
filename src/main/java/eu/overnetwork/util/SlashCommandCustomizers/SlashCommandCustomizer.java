package eu.overnetwork.util.SlashCommandCustomizers;

import org.javacord.api.interaction.SlashCommandBuilder;

public class SlashCommandCustomizer extends SlashCommandCustomizerInterface{
    private final SlashCommandBuilder command;

    /**
     * Constructor for the customizer
     * @param command the slash command to be customized
     */
    public SlashCommandCustomizer(SlashCommandBuilder command){
        this.command = command;
    }

    /**
     * Method to save the customizations done to the slash command
     * @return the customized slash command builder
     */
    public SlashCommandBuilder setCustomizations(){
        if (this.getSubCommandGroupList().isEmpty()){
            if (this.getSubCommandGroupList().isEmpty()){
                if (this.getOptionList().isEmpty()){
                    // Option-less slash command handling perhaps? -> not required
                    System.out.println(String.format("No options set for this slash command: %s", this.command.toString()));
                }
                else {
                    this.command.setOptions(this.getOptionList());
                }
            }
            else{
                this.command.setOptions(this.getSubCommandGroupList());
            }
        }
        else{
            this.command.setOptions(this.getSubCommandGroupList());
        }
        return this.command;
    }
}
