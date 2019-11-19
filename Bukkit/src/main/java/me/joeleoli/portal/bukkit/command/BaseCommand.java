package me.joeleoli.portal.bukkit.command;

import me.joeleoli.portal.bukkit.Portal;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements CommandExecutor {

    protected static final String NO_PERMISSION = ChatColor.RED + "No permission.";
    protected static final String CONSOLE_SENDER = ChatColor.RED + "This command can only be peformed in-game.";

    public BaseCommand(String name) {
        Portal.getInstance().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("Command not handled");
        return true;
    }

}