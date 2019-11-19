package me.joeleoli.portal.bukkit.command.commands;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.command.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueMenuCommand extends BaseCommand {

    public QueueMenuCommand() {
        super("queuemenu");
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(CONSOLE_SENDER);
            return true;
        }
        if (!commandSender.hasPermission("portal.menu") && !commandSender.isOp()) {
            commandSender.sendMessage(NO_PERMISSION);
            return true;
        }

        Player player = (Player) commandSender;
        Portal.getInstance().menus.queueStatusMenu(player);

        return true;
    }
}
