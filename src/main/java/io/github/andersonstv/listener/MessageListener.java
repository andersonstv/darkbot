/*
 *     NecronomiBot. A Discord Bot for use with RPGs (RolePlaying Games)
 *     Copyright (C) 2020  Anderson dos Santos Silva
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.andersonstv.listener;


import io.github.andersonstv.dice.DiceUtil;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    public MessageListener() {
        super();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String messageContent = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        String[] messageArray = messageContent.split(" ");
        String response;
        if (messageContent.charAt(0) == '$') {
            response = switch (messageArray[0]) {
                case "$ping" -> "Pong!";
                case "$roll" -> DiceUtil.simple(messageContent);
                case "$wod" -> DiceUtil.wod(messageContent);
                case "$coc" -> DiceUtil.coc(messageContent);
                case "$uwu" -> uwunator(messageContent);
                default -> "Command not recognized";
            };
            channel.sendMessage(response).queue();
        }
    }
    public String uwunator(String messageContent){
        String response = messageContent.replace("$uwu", "").replaceAll("r|l", "w");
        return response.replaceAll("n(a|e|i|o|u)", "ny$1");
    }

}
