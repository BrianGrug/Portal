package me.joeleoli.portal.bukkit.command.commands;

import me.joeleoli.portal.bukkit.command.BaseCommand;
import me.joeleoli.portal.shared.queue.Queue;
import me.joeleoli.portal.shared.server.ServerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DataDumpCommand extends BaseCommand {

    public DataDumpCommand() {
        super("datadump");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("portal.datadump") && !commandSender.isOp()) {
            commandSender.sendMessage(NO_PERMISSION);
            return true;
        }

        commandSender.sendMessage("Servers:");

        for (ServerData serverData : ServerData.getServers()) {
            StringBuilder builder = new StringBuilder("- ")
                    .append(serverData.getName())
                    .append(" (")
                    .append(serverData.isOnline())
                    .append(") (")
                    .append(serverData.getOnlinePlayers())
                    .append("/")
                    .append(serverData.getMaximumPlayers())
                    .append(")");

            commandSender.sendMessage(builder.toString());
        }

        commandSender.sendMessage("Queues:");

        for (Queue queue : Queue.getQueues()) {
            StringBuilder builder = new StringBuilder("- ")
                    .append(queue.getName())
                    .append(" (")
                    .append(queue.getPlayers().size())
                    .append(" in queue)");

            ServerData serverData = queue.getServerData();

            if (serverData == null) {
                builder
                        .append(" (offline)");
            } else {
                builder
                        .append(" (Online: ")
                        .append(serverData.isOnline())
                        .append(") (Whitelisted: ")
                        .append(serverData.isWhitelisted())
                        .append(") ")
                        .append("(Paused: ")
                        .append(queue.isEnabled())
                        .append(") (")
                        .append(serverData.getOnlinePlayers())
                        .append("/")
                        .append(serverData.getMaximumPlayers())
                        .append(")");
            }

            commandSender.sendMessage(builder.toString());
        }

        return true;
    }

}
