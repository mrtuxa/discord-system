package eu.overnetwork.util;

import com.thoughtworks.qdox.model.expression.Constant;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.io.File;

public class CustomEmbedBuilder extends EmbedBuilder {
    public CustomEmbedBuilder() {
        super();
        super.setAuthor("Over-Network System", null, new File("over-hosting_new.png"));
        super.setColor(Color.BLACK);
        super.setFooter("by Over-Network");
    }
}
