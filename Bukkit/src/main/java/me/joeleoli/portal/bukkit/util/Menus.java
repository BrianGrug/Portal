package me.joeleoli.portal.bukkit.util;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.config.Language;
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

        if(queue == null) {
            return ChatColor.RED + "This queue does not exist";
        }

        return queue.isEnabled() ? ChatColor.GREEN + "This queue is available" : ChatColor.RED + "This queue is unavailable";
    }

    public void queueStatusMenu(Player player){
        Language l = Portal.getInstance().getLanguage();
        inv = Bukkit.createInventory(null, InventoryType.PLAYER, l.getTitle());
        for(Queue q : Queue.getQueues()) {
            Queue queue = Queue.getByName(q.getName());

            if(queue.getServerData() != null) {
                if (queue.getServerData().isOnline() && !queue.getServerData().isWhitelisted()) {
                    inv.addItem(new ItemBuilder(Material.EMERALD_BLOCK)
                            .addLore(l.getServerstatus() + l.getOnline(),
                                    l.getServerplayers() + l.getPlayercount(queue),
                                    l.getQueuestatus() + queue.statusString())
                            .setDisplayName(ChatColor.GREEN + l.getQueuename(queue))
                            .create());
                }
                if (queue.getServerData().isOnline() & queue.getServerData().isWhitelisted()) {
                    inv.addItem(new ItemBuilder(Material.QUARTZ_BLOCK)
                            .addLore(l.getServerstatus() + l.getWhitelisted(),
                                    l.getServerplayers() + l.getPlayercount(queue),
                                    l.getQueuestatus() + queue.statusString())
                            .setDisplayName(ChatColor.WHITE + l.getQueuename(queue))
                            .create());
                }
                player.openInventory(inv);
            }
            if (queue.getServerData() == null || !queue.getServerData().isOnline()) {
                inv.addItem(new ItemBuilder(Material.REDSTONE_BLOCK)
                        .addLore(l.getServerstatus() + l.getOffline(),
                                l.getServerplayers() + l.getNoplayers(),
                                l.getQueuestatus() + queue.statusString())
                        .setDisplayName(ChatColor.RED + l.getQueuename(queue))
                        .create());
            }
            player.openInventory(inv);
        }
    }
}
