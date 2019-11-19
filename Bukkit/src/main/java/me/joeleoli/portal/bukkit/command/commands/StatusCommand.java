package me.joeleoli.portal.bukkit.command.commands;

import me.joeleoli.portal.bukkit.command.BaseCommand;
import me.joeleoli.portal.shared.queue.Queue;
import me.joeleoli.portal.shared.server.ServerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class StatusCommand extends BaseCommand {

    public StatusCommand() {
        super("status");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!commandSender.hasPermission("portal.status")){
            commandSender.sendMessage(NO_PERMISSION);
            return true;
        }
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /status <server>");
            return true;
        }
        Queue queue = Queue.getByName(args[0]);
        ServerData serverData = queue.getServerData();

        commandSender.sendMessage("");
        return true;
    }
}
