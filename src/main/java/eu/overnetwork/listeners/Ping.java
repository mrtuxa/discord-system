package eu.overnetwork.listeners;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class Ping implements MessageCreateListener {

    @Override
    public void onMessageCreate(@NotNull MessageCreateEvent event) {
        Settings cfg = new Settings();
        if (event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "ping")) {
            Color pingEmbedColor = new Color(255, 0, 0);
            long gatewayLatency =
                   event.getApi().getLatestGatewayLatency().toMillis();
            event.getApi().measureRestLatency().thenAccept(Time -> {
                EmbedBuilder initialPing = new EmbedBuilder()
                        .setTitle("***Testing Ping... :ping_pong: ***")
                        .setColor(pingEmbedColor)
                        .setFooter(event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getAvatar());

                long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
                String time = String.format("%d days, %d hours, %d minutes, %d seconds",
                        TimeUnit.MILLISECONDS.toDays(uptime),
                        TimeUnit.MILLISECONDS.toHours(uptime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(uptime)),
                        TimeUnit.MILLISECONDS.toMinutes(uptime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptime)),
                        TimeUnit.MILLISECONDS.toSeconds(uptime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptime))
                );

                long totalMemory = (Runtime.getRuntime().totalMemory() / 1024) / 1024;
                long usedMemory = ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1024;

                EmbedBuilder pingEmbed = new EmbedBuilder()
                        .setTitle("**Latency of the Bot:** ")
                        .setDescription("Current Ping")
                        .addField("Gateway Latency", "`" + gatewayLatency + "ms" + "`" + "\nTotal Memory: " + "`" +  totalMemory + "MB`" + "\nUsed Memory: " + "`" + usedMemory  + "MB`" + "\nUptime: " + "`" + time + "`" + "\nTotal Server: " + "`"+ event.getApi().getServers().size() + " servers`", false)
                        .setFooter(event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getAvatar())
                        .setColor(pingEmbedColor);
                event.getChannel().sendMessage(initialPing).thenAccept(message -> message.getApi().getThreadPool().getScheduler().schedule(() -> {
                    message.edit(pingEmbed);
                }, 2, TimeUnit.SECONDS));
           });
        }
    }
}
