package me.joeleoli.portal.bukkit.util;

import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class Menus {

    private Inventory inv;

    public String statusString(String server){
        Queue queue = Queue.getByName(server);

        return queue.isEnabled() ? ChatColor.GREEN + "This queue is available" : ChatColor.RED + "This queue is unavailable";
    }

    public void queueMenu(Player player) {
        inv = Bukkit.createInventory(null, 18, "Queue Manager");

        inv.setItem(12, new ItemBuilder(Material.EMERALD_BLOCK).setDisplayName(ChatColor.GREEN + "Queue status'").create());
    }

    public void queueStatusMenu(Player player){
        inv = Bukkit.createInventory(null, InventoryType.PLAYER, "Queue Status");
        for(Queue q : Queue.getQueues()) {
            Queue queue = Queue.getByName(q.getName());

            if(queue.getServerData() != null) {
                if (queue.getServerData().isOnline() && !queue.getServerData().isWhitelisted()) {
                    inv.addItem(new ItemBuilder(Material.EMERALD_BLOCK)
                            .addLore(ChatColor.YELLOW + "Status » " + ChatColor.GREEN + "Online.",
                                    ChatColor.YELLOW + "Players » " + ChatColor.GREEN + queue.getServerData().getOnlinePlayers() + "/" + queue.getServerData().getMaximumPlayers(),
                                    ChatColor.YELLOW + "Queue Status » " + queue.statusString())
                            .setDisplayName(ChatColor.GREEN + queue.getName())
                            .create());
                }
                if (queue.getServerData().isOnline() & queue.getServerData().isWhitelisted()) {
                    inv.addItem(new ItemBuilder(Material.QUARTZ_BLOCK)
                            .addLore(ChatColor.YELLOW + "Status » " + ChatColor.WHITE + "Whitelisted.",
                                    ChatColor.YELLOW + "Players » " + ChatColor.GREEN + queue.getServerData().getOnlinePlayers() + "/" + queue.getServerData().getMaximumPlayers(),
                                    ChatColor.YELLOW + "Queue Status » " + queue.statusString())
                            .setDisplayName(ChatColor.GREEN + queue.getName())
                            .create());
                }
                player.openInventory(inv);
            }
            if (queue.getServerData() == null || !queue.getServerData().isOnline()) {
                inv.addItem(new ItemBuilder(Material.REDSTONE_BLOCK)
                        .addLore(ChatColor.YELLOW + "Status » " + ChatColor.RED + "Offline.",
                                ChatColor.YELLOW + "Players » " + "0/0",
                                ChatColor.YELLOW + "Queue Status » " + queue.statusString())
                        .setDisplayName(ChatColor.RED + queue.getName())
                        .create());
            }
            player.openInventory(inv);
        }
    }
}
