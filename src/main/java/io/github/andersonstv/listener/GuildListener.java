package io.github.andersonstv.listener;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {
    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        MessageChannel channel = event.getGuild().getSystemChannel();
        String sep = System.lineSeparator();
        String introMessage = "Hello, I'm DarkBot, a bot made to help running Chronicles of Darkness " +
                "on Discord. You can check out the source code at: " +
                "https://github.com/andersonstv/darkbot";
        if (channel != null){
            channel.sendMessage(introMessage).queue();
        }
    }
}
