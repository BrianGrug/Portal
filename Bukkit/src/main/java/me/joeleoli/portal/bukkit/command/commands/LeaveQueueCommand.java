package me.joeleoli.portal.bukkit.command.commands;

import com.google.gson.JsonObject;
import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.command.BaseCommand;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveQueueCommand extends BaseCommand {

    public LeaveQueueCommand() {
        super("leavequeue");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(CONSOLE_SENDER);
            return true;
        }

        Player player = (Player) commandSender;

        Queue queue = Queue.getByPlayer(player.getUniqueId());

        if (queue == null) {
            player.sendMessage(ChatColor.RED + "You are not in a queue.");
            return true;
        }

        JsonObject data = new JsonObject();
        data.addProperty("uuid", player.getUniqueId().toString());

        Portal.getInstance().getPublisher().write(JedisChannel.INDEPENDENT, JedisAction.REMOVE_PLAYER, data);

        return true;
    }

}
