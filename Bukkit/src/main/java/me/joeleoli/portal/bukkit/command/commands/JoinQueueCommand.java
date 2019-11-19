package me.joeleoli.portal.bukkit.command.commands;

import com.google.gson.JsonObject;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.command.BaseCommand;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.queue.Queue;
import me.joeleoli.portal.shared.queue.QueueRank;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinQueueCommand extends BaseCommand {

    public JoinQueueCommand() {
        super("joinqueue");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(CONSOLE_SENDER);
            return true;
        }

        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /joinqueue <server>");
            return true;
        }

        Player bukkitPlayer = (Player) commandSender;

        Queue queue = Queue.getByPlayer(bukkitPlayer.getUniqueId());

        if (queue != null) {
            bukkitPlayer.sendMessage(ChatColor.RED + "You are already in a queue.");
            return true;
        }

        queue = Queue.getByName(args[0]);

        if (queue == null) {
            bukkitPlayer.sendMessage(ChatColor.RED + "That queue does not exist or is offline.");
            return true;
        }

        if (queue.getServerData() == null || !queue.getServerData().isOnline()) {
            bukkitPlayer.sendMessage(ChatColor.RED + "That queue is offline.");
            return true;
        }

        if (bukkitPlayer.hasPermission("portal.bypass")) {
            JsonObject data = new JsonObject();
            data.addProperty("uuid", bukkitPlayer.getUniqueId().toString());
            data.addProperty("server", queue.getName());

            Portal.getInstance().getPublisher().write(JedisChannel.BUKKIT, JedisAction.SEND_PLAYER_SERVER, data);

            return true;
        }

        QueueRank queueRank = Portal.getInstance().getPriorityProvider().getPriority(bukkitPlayer);

        JsonObject rank = new JsonObject();
        rank.addProperty("name", queueRank.getName());
        rank.addProperty("priority", queueRank.getPriority());

        JsonObject player = new JsonObject();
        player.addProperty("uuid", bukkitPlayer.getUniqueId().toString());
        player.add("rank", rank);

        JsonObject data = new JsonObject();
        data.addProperty("queue", queue.getName());
        data.add("player", player);

        Portal.getInstance().getPublisher().write(JedisChannel.INDEPENDENT, JedisAction.ADD_PLAYER, data);

        return true;
    }

}
