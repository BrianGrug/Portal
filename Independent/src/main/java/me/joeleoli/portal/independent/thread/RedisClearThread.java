package me.joeleoli.portal.independent.thread;

import me.joeleoli.portal.independent.Portal;

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
