package me.joeleoli.portal.bukkit.jedis;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.util.BungeeUtil;
import me.joeleoli.portal.shared.jedis.JedisSubscriptionHandler;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.queue.Queue;
import me.joeleoli.portal.shared.queue.QueuePlayer;
import me.joeleoli.portal.shared.queue.QueuePlayerComparator;
import me.joeleoli.portal.shared.queue.QueueRank;
import me.joeleoli.portal.shared.server.ServerData;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.PriorityQueue;
import java.util.UUID;

public class PortalSubscriptionHandler implements JedisSubscriptionHandler {

    @Override
    public void handleMessage(JsonObject json) {
        JedisAction action = JedisAction.valueOf(json.get("action").getAsString());
        JsonObject data = json.get("data").isJsonNull() ? null : json.get("data").getAsJsonObject();

        if (data == null) {
            return;
        }

        switch (action) {
            case UPDATE: {
                final String name = data.get("name").getAsString();

                if (!Portal.getInstance().getPortalServer().isHub()) {
                    return;
                }

                ServerData serverData = ServerData.getByName(name);

                if (serverData == null) {
                    serverData = new ServerData(name);
                }

                serverData.setOnlinePlayers(data.get("online-players").getAsInt());
                serverData.setMaximumPlayers(data.get("maximum-players").getAsInt());
                serverData.setWhitelisted(data.get("whitelisted").getAsBoolean());
                serverData.setLastUpdate(System.currentTimeMillis());
            }
            break;
            case LIST: {

                for (JsonElement e : data.get("queues").getAsJsonArray()) {
                    final JsonObject queueJson = e.getAsJsonObject();
                    final String name = queueJson.get("name").getAsString();

                    Queue queue = Queue.getByName(name);

                    if (queue == null) {
                        queue = new Queue(name);
                    }

                    PriorityQueue<QueuePlayer> players = new PriorityQueue<>(new QueuePlayerComparator());

                    for (JsonElement pe : queueJson.get("players").getAsJsonArray()) {
                        JsonObject player = pe.getAsJsonObject();
                        JsonObject rank = player.get("rank").getAsJsonObject();

                        QueueRank queueRank = new QueueRank();

                        queueRank.setName(rank.get("name").getAsString());
                        queueRank.setPriority(rank.get("priority").getAsInt());

                        QueuePlayer queuePlayer = new QueuePlayer();

                        queuePlayer.setUuid(UUID.fromString(player.get("uuid").getAsString()));
                        queuePlayer.setRank(queueRank);
                        queuePlayer.setInserted(player.get("inserted").getAsLong());

                        players.add(queuePlayer);
                    }

                    queue.setPlayers(players);
                    queue.setEnabled(queueJson.get("status").getAsBoolean());
                }
            }
            break;
            case ADDED_PLAYER: {
                Queue queue = Queue.getByName(data.get("queue").getAsString());

                if (queue == null) {
                    return;
                }

                JsonObject player = data.get("player").getAsJsonObject();
                JsonObject rank = player.get("rank").getAsJsonObject();

                QueueRank queueRank = new QueueRank();
                queueRank.setName(rank.get("name").getAsString());
                queueRank.setPriority(rank.get("priority").getAsInt());

                QueuePlayer queuePlayer = new QueuePlayer();
                queuePlayer.setUuid(UUID.fromString(player.get("uuid").getAsString()));
                queuePlayer.setRank(queueRank);
                queuePlayer.setInserted(player.get("inserted").getAsLong());

                queue.getPlayers().add(queuePlayer);

                Player bukkitPlayer = Portal.getInstance().getServer().getPlayer(queuePlayer.getUuid());

                if (bukkitPlayer != null) {
                    for (String message : Portal.getInstance().getLanguage().getAdded(bukkitPlayer, queue)) {
                        bukkitPlayer.sendMessage(message);
                    }
                }
            }
            break;
            case REMOVED_PLAYER: {
                Queue queue = Queue.getByName(data.get("queue").getAsString());

                if (queue == null) {
                    return;
                }

                UUID uuid = UUID.fromString(data.get("player").getAsJsonObject().get("uuid").getAsString());

                queue.getPlayers().removeIf(queuePlayer -> queuePlayer.getUuid().equals(uuid));

                Player bukkitPlayer = Portal.getInstance().getServer().getPlayer(uuid);

                if (bukkitPlayer != null) {
                    for (String message : Portal.getInstance().getLanguage().getRemoved(queue)) {
                        bukkitPlayer.sendMessage(message);
                    }
                }
            }
            break;
            case SEND_PLAYER_SERVER: {
                String server = data.get("server").getAsString();

                Player player;

                // Send player by username or uuid
                if (data.has("username")) {
                    player = Portal.getInstance().getServer().getPlayer(data.get("username").getAsString());
                } else {
                    player = Portal.getInstance().getServer().getPlayer(UUID.fromString(data.get("uuid").getAsString()));
                }

                if (player == null) {
                    return;
                }

                player.sendMessage(ChatColor.GREEN + "Sending you to " + server);
                BungeeUtil.sendToServer(player, server);
            }
            break;
            case MESSAGE_PLAYER: {
                Player player = Portal.getInstance().getServer().getPlayer(data.get("uuid").getAsString());

                if (player == null) {
                    return;
                }

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', data.get("message").getAsString()));
            }
            break;
        }
    }

}
