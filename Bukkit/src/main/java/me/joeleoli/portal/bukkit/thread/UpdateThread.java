package me.joeleoli.portal.bukkit.thread;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.shared.jedis.JedisAction;
import me.joeleoli.portal.shared.jedis.JedisChannel;

public class UpdateThread extends Thread {

    @Override
    public void run() {
        while (true) {
            Portal.getInstance().getPublisher().write(JedisChannel.INDEPENDENT, JedisAction.UPDATE, Portal.getInstance().getPortalServer().getServerData());
            Portal.getInstance().getPublisher().write(JedisChannel.BUKKIT, JedisAction.UPDATE, Portal.getInstance().getPortalServer().getServerData());

            try {
                Thread.sleep(2500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
