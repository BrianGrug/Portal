package me.joeleoli.portal.independent.thread;

import com.google.gson.JsonObject;
import me.joeleoli.portal.independent.Portal;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.queue.Queue;
import me.joeleoli.portal.shared.queue.QueuePlayer;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.server.ServerData;

public class QueueThread extends Thread {


    private static final Long SEND_DELAY = (long) Portal.getInstance().getConfig().getDelay();

    @Override
    public void run() {
        while (Portal.getInstance().threads) {
            for (Queue queue : Queue.getQueues()) {
                ServerData serverData = queue.getServerData();

                if (serverData == null) {
                    continue;
                }

                if (!queue.isEnabled()) {
                    continue;
                }

                if (!serverData.isOnline()) {
                    continue;
                }

                if (serverData.isWhitelisted()) {
                    continue;
                }

                if (serverData.getOnlinePlayers() >= serverData.getMaximumPlayers()) {
                    continue;
                }

                QueuePlayer next = queue.getPlayers().poll();

                if (next != null) {
                    JsonObject data = new JsonObject();
                    data.addProperty("server", queue.getName());
                    data.addProperty("uuid", next.getUuid().toString());

                    Portal.getInstance().getPublisher().write(JedisChannel.BUKKIT, JedisAction.SEND_PLAYER_SERVER, data);
                }
            }

            try {
                Thread.sleep(SEND_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
