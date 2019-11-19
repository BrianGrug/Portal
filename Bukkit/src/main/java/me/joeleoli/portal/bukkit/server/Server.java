package me.joeleoli.portal.bukkit.server;

import com.google.gson.JsonObject;
import me.joeleoli.portal.bukkit.Portal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Server {

    private String name;
    private boolean hub;

    public JsonObject getServerData() {
        JsonObject object = new JsonObject();
        object.addProperty("name", this.name);
        object.addProperty("online-players", Portal.getInstance().getServer().getOnlinePlayers().size());
        object.addProperty("maximum-players", Portal.getInstance().getServer().getMaxPlayers());
        object.addProperty("whitelisted", Portal.getInstance().getServer().hasWhitelist());
        return object;
    }

}
