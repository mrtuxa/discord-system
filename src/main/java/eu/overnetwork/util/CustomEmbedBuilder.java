package eu.overnetwork.util;

import eu.overnetwork.core.Constant;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.io.File;

public class CustomEmbedBuilder extends EmbedBuilder {
    public CustomEmbedBuilder() {
        super();
        super.setAuthor("Over-Network System", null, new File("over-hosting_new.png"));
        super.setColor(Constant.JAVACORD_ORANGE);
        super.setFooter("by Over-Network");
    }
}
