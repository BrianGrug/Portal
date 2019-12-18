package me.joeleoli.portal.independent.thread;

import me.joeleoli.portal.independent.Portal;
import redis.clients.jedis.JedisCluster;

import javax.sound.sampled.Port;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class RedisClearThread extends Thread{

    @Override
    public void run() {
        Portal.getInstance().getSubscriber().resetRedis();
        try {
            Thread.sleep(3600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
