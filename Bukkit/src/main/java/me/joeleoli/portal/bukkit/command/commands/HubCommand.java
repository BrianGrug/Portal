package me.joeleoli.portal.bukkit.command.commands;

import com.google.gson.JsonObject;
import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.command.BaseCommand;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.queue.QueueRank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand extends BaseCommand {

    public HubCommand() {
        super("hub");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(CONSOLE_SENDER);
            return true;
        }

        Player bukkitPlayer = (Player) commandSender;

        JsonObject data = new JsonObject();
        data.addProperty("uuid", bukkitPlayer.getUniqueId().toString());

        Portal.getInstance().getPublisher().write(JedisChannel.INDEPENDENT, JedisAction.SEND_PLAYER_HUB, data);


        return true;
    }
}
