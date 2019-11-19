package me.joeleoli.portal.shared.jedis;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class JedisQueue {

    @NonNull private String channel;
    @NonNull private JedisAction action;
    @NonNull private JsonObject data;

}
