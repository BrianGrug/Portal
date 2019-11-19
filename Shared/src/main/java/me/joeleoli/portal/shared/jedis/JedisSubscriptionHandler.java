package me.joeleoli.portal.shared.jedis;

import com.google.gson.JsonObject;

public interface JedisSubscriptionHandler {

    void handleMessage(JsonObject json);

}
