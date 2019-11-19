package me.joeleoli.portal.shared.queue;

import com.google.gson.JsonObject;
import me.joeleoli.portal.shared.jedis.*;
import me.joeleoli.portal.shared.server.ServerData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class Queue {



    @Getter
    private static List<Queue> queues = new ArrayList<>();

    public JedisSettings jedisSettings;
    private String name;
    @Setter private PriorityQueue<QueuePlayer> players = new PriorityQueue<>(new QueuePlayerComparator());
    @Setter private boolean enabled;

    public Queue(String name) {
        this.name = name;

        queues.add(this);
    }

    public String statusString(){
        return enabled? ChatColor.GREEN + "Active" : ChatColor.RED + "Disabled";
    }

    public String serverStatus(){
        return getServerData().isOnline()? ChatColor.GREEN + "Online" : getServerData().isWhitelisted()? ChatColor.WHITE + "Whitelisted" : ChatColor.RED + "Offline";
    }

    public ServerData getServerData() {
        return ServerData.getByName(this.name);
    }

    public boolean containsPlayer(UUID uuid) {
        for (QueuePlayer player : this.players) {
            if (player.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public int getPosition(UUID uuid) {
        if (!this.containsPlayer(uuid)) {
            return 0;
        }

        PriorityQueue<QueuePlayer> queue = new PriorityQueue<>(this.players);

        int position = 0;

        while (!queue.isEmpty()) {
            QueuePlayer player = queue.poll();

            if (player.getUuid().equals(uuid)) {
                break;
            }

            position++;
        }

        return position + 1;
    }

    @Override
    public String toString() {
        return "";
    }

    public static Queue getByName(String name) {
        for (Queue queue : Queue.getQueues()) {
            if (queue.getName().equalsIgnoreCase(name)) {
                return queue;
            }
        }
        return null;
    }

    public static Queue getByPlayer(UUID uuid) {
        for (Queue queue : Queue.getQueues()) {
            for (QueuePlayer queuePlayer : queue.getPlayers()) {
                if (queuePlayer.getUuid().equals(uuid)) {
                    return queue;
                }
            }
        }
        return null;
    }
    public void sendPlayer(Player player, String server){
        player.performCommand("joinqueue " + server);
    }
}
