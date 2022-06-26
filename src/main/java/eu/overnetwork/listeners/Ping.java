package eu.overnetwork.listeners;

import eu.overnetwork.cfg.Settings;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Ping implements MessageCreateListener {
    /**
     * @param event
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Settings cfg = new Settings();
        if (event.getMessageContent().equalsIgnoreCase(cfg.getPrefix() + "ping")) {
            Color pingEmbedColor = new Color(255, 0, 0);
            long GatewayLatency =
                   event.getApi().getLatestGatewayLatency().toMillis();
            CompletableFuture<Void> RESTLatency = event.getApi().measureRestLatency().thenAccept(Time -> {
                EmbedBuilder InitialPing = new EmbedBuilder()
                        .setTitle("***Testing Ping... :ping_pong: ***")
                        .setColor(pingEmbedColor)
                        .setFooter(event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getAvatar());

                EmbedBuilder PingEmbed = new EmbedBuilder()
                        .setTitle("**Latency of the Bot:** ")
                        .setDescription("Current Ping")
                        .addField("Gateway Latency", "`" + GatewayLatency + "ms" + "`" + "\nTotal Memory: " + "`" +  totalMemory() + "MB`" + "\nUsed Memory: " + "`" + usedMemory()  + "MB`" + "\nUptime: " + "`" + onTime() + "`" + "\nTotal Server: " + "`"+ event.getApi().getServers().size() + " servers`", false)
                        .setFooter(event.getMessageAuthor().getDisplayName(), event.getMessageAuthor().getAvatar())
                        .setColor(pingEmbedColor);
                event.getChannel().sendMessage(InitialPing).thenAccept(MessageToBeEdited -> MessageToBeEdited.getApi().getThreadPool().getScheduler().schedule(() -> {
                    MessageToBeEdited.edit(PingEmbed);
                }, 2, TimeUnit.SECONDS));
           });
        }
    }

    public String onTime() {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        return String.format("%d days, %d hours, %d minutes, %d seconds",
                TimeUnit.MILLISECONDS.toDays(uptime),
                TimeUnit.MILLISECONDS.toHours(uptime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(uptime)),
                TimeUnit.MILLISECONDS.toMinutes(uptime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(uptime)),
                TimeUnit.MILLISECONDS.toSeconds(uptime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(uptime))
        );
    }

    private long totalMemory() {
        return (Runtime.getRuntime().totalMemory() / 1024) / 1024;
    }

    private long usedMemory() {
        return ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1024;
    }
}
