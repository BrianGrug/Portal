package me.joeleoli.portal.independent.thread;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.joeleoli.portal.independent.Portal;
import me.joeleoli.portal.independent.log.Logger;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.queue.Queue;
import me.joeleoli.portal.shared.queue.QueuePlayer;
import me.joeleoli.portal.shared.server.ServerData;

public class BroadcastThread extends Thread {

    @Override
    public void run() {
        while (true) {
            JsonArray queues = new JsonArray();

            for (Queue queue : Queue.getQueues()) {
                JsonArray players = new JsonArray();

                for (QueuePlayer player : queue.getPlayers()) {
                    JsonObject rank = new JsonObject();
                    rank.addProperty("name", player.getRank().getName());
                    rank.addProperty("priority", player.getRank().getPriority());

                    JsonObject json = new JsonObject();
                    json.addProperty("uuid", player.getUuid().toString());
                    json.addProperty("inserted", player.getInserted());
                    json.add("rank", rank);

                    players.add(json);
                }

                JsonObject json = new JsonObject();
                json.addProperty("name", queue.getName());
                json.addProperty("status", queue.isEnabled());
                json.add("players", players);

                queues.add(json);
            }

            JsonObject json = new JsonObject();

            json.add("queues", queues);

            Portal.getInstance().getPublisher().write(JedisChannel.BUKKIT, JedisAction.LIST, json);

            Logger.print("Broadcasted server and queue list");

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
