package me.joeleoli.portal.bukkit.listener;

import com.google.gson.JsonObject;
import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        JsonObject data = new JsonObject();
        data.addProperty("uuid", event.getPlayer().getUniqueId().toString());

        Portal.getInstance().getPublisher().write(JedisChannel.INDEPENDENT, JedisAction.REMOVE_PLAYER, data);
    }
}
