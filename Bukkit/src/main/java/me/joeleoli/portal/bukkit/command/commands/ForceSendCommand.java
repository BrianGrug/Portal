package me.joeleoli.portal.bukkit.command.commands;

import com.google.gson.JsonObject;
import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.command.BaseCommand;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceSendCommand extends BaseCommand {

    public ForceSendCommand() {
        super("forcesend");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("portal.forcesend") && !commandSender.isOp()) {
            commandSender.sendMessage(NO_PERMISSION);
            return true;
        }

        if (args.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /forcesend <username> <server>");
            return true;
        }

        JsonObject json = new JsonObject();
        json.addProperty("username", args[0]);
        json.addProperty("server", args[1]);

        Portal.getInstance().getPublisher().write(JedisChannel.BUKKIT, JedisAction.SEND_PLAYER_SERVER, json);

        commandSender.sendMessage(ChatColor.GREEN + "If a player with that username is online, they will be sent to `" + args[1] + "`.");

        return true;
    }

}
