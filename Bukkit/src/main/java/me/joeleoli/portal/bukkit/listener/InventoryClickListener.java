package me.joeleoli.portal.bukkit.listener;

import com.google.gson.JsonObject;
import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void clickListener(InventoryClickEvent event) {
        if(event.getClickedInventory() != null){
        if (event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem() != null && event.getInventory().getTitle().equals("Queue Status")) {
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);


            Queue queue = Queue.getByName(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));

            if (queue != null) {
                queue.setEnabled(!queue.isEnabled());
            }

            JsonObject json = new JsonObject();
            json.addProperty("queue", queue.getName());

            Portal.getInstance().getPublisher().write(JedisChannel.INDEPENDENT, JedisAction.TOGGLE, json);

            player.sendMessage(ChatColor.GREEN + "Changed status of `" + queue.getName() + "` to " + queue.isEnabled());
            player.closeInventory();
            Portal.getInstance().menus.queueStatusMenu(player);
        }
        }
    }
}