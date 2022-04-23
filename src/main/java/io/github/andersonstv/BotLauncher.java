package io.github.andersonstv;

import io.github.andersonstv.listener.GuildListener;
import io.github.andersonstv.listener.MessageListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class  BotLauncher {
    public static void main(String[] args) throws Exception{
        Dotenv dotenv = Dotenv.load();
        JDA api = JDABuilder.createDefault(dotenv.get("DISCORD_TOKEN")).build();
        registerListener(api);
    }
    static void registerListener(JDA api){

        api.addEventListener(new MessageListener());
        api.addEventListener(new GuildListener());
    }
}
