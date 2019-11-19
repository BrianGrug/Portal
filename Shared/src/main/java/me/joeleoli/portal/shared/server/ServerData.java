package me.joeleoli.portal.shared.server;

import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Data
public class ServerData {

    @Getter
    private static Set<ServerData> servers = new HashSet<>();

    private String name;
    private int onlinePlayers;
    private int maximumPlayers;
    private boolean whitelisted;
    private long lastUpdate;

    public ServerData(String name) {
        this.name = name;

        servers.add(this);
    }

    public boolean isOnline() {
        return System.currentTimeMillis() - this.lastUpdate < 15000L;
    }

    @Override
    public String toString() {
        return "";
    }

    public static ServerData getByName(String name) {
        for (ServerData server : servers) {
            if (server.getName().equalsIgnoreCase(name)) {
                return server;
            }
        }

        return null;
    }

}
