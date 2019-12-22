package me.joeleoli.portal.bukkit.config;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.shared.queue.Queue;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Language {

    private List<String> reminder = Arrays.asList(
            "&eYou are position &d#{position} &eof &d{total} &ein the &a{queue} &equeue.",
            "&7Purchase a rank at www.server.net to get a higher queue priority."
    );
    private List<String> added = Arrays.asList(
            "&aYou have joined the {queue} queue."
    );
    private List<String> removed = Arrays.asList(
            "&cYou have been removed from the {queue} queue."
    );
    private List<String> permission = Arrays.asList(
            "&cNo permission."
    );
    private List<String> title = Arrays.asList(
            "Queue Status"
    );
    private List<String> queuename = Arrays.asList(
            "&a{queue}"
    );
    private List<String> serverstatus = Arrays.asList(
            "&eStatus » "
    );
    private List<String> serverplayers = Arrays.asList(
            "&ePlayers » "
    );
    private List<String> playercount = Arrays.asList(
            "&a{online}/{max}"
    );
    private List<String> noplayers = Arrays.asList(
            "&c0/0"
    );
    private List<String> queuestatus = Arrays.asList(
            "&eQueue Status » "
    );
    private List<String> online = Arrays.asList(
            "&aOnline"
    );
    private List<String> whitelisted = Arrays.asList(
            "&fWhitelisted"
    );
    private List<String> offline = Arrays.asList(
            "&cOffline"
    );

    public void load() {
        FileConfiguration config = Portal.getInstance().getMainConfig().getConfig();

        if (config.contains("language.reminder")) {
            this.reminder = config.getStringList("language.reminder");
        }

        if (config.contains("language.added")) {
            this.added = config.getStringList("language.added");
        }

        if (config.contains("language.removed")) {
            this.removed = config.getStringList("language.removed");
        }
        if (config.contains("language.permission")) {
            this.permission = config.getStringList("language.permission");
        }
        if (config.contains("language.queuemenu.title")) {
            this.title = config.getStringList("language.queuemenu.title");
        }
        if (config.contains("language.queuemenu.queuename")) {
            this.queuename = config.getStringList("language.queuemenu.queuename");
        }
        if (config.contains("language.queuemenu.serverstatus")) {
            this.serverstatus = config.getStringList("language.queuemenu.serverstatus");
        }
        if (config.contains("language.queuemenu.serverplayers")) {
            this.serverplayers = config.getStringList("language.queuemenu.serverplayers");
        }
        if (config.contains("language.queuemenu.playercount")) {
            this.playercount = config.getStringList("language.queuemenu.playercount");
        }
        if (config.contains("language.queuemenu.noplayers")) {
            this.noplayers = config.getStringList("language.queuemenu.noplayers");
        }
        if (config.contains("language.queuemenu.queuestatus")) {
            this.queuestatus = config.getStringList("language.queuemenu.queuestatus");
        }
        if (config.contains("language.queuemenu.online")) {
            this.online = config.getStringList("language.queuemenu.online");
        }
        if (config.contains("language.queuemenu.whitelisted")) {
            this.whitelisted = config.getStringList("language.queuemenu.whitelisted");
        }
        if (config.contains("language.queuemenu.offline")) {
            this.offline = config.getStringList("language.queuemenu.offline");
        }
    }

    public List<String> getReminder(Player player, Queue queue) {
        List<String> translated = new ArrayList<>();

        for (String line : this.reminder) {
            translated.add(ChatColor.translateAlternateColorCodes('&', line
                    .replace("{position}", queue.getPosition(player.getUniqueId()) + "")
                    .replace("{total}", queue.getPlayers().size() + "")
                    .replace("{queue}", queue.getName()))
            );
        }

        return translated;
    }

    public List<String> getAdded(Player player, Queue queue) {
        List<String> translated = new ArrayList<>();

        for (String line : this.added) {
            translated.add(ChatColor.translateAlternateColorCodes('&', line
                    .replace("{position}", queue.getPosition(player.getUniqueId()) + "")
                    .replace("{total}", queue.getPlayers().size() + "")
                    .replace("{queue}", queue.getName()))
            );
        }

        return translated;
    }

    public List<String> getRemoved(Queue queue) {
        List<String> translated = new ArrayList<>();

        for (String line : this.removed) {
            translated.add(ChatColor.translateAlternateColorCodes('&', line
                    .replace("{queue}", queue.getName()))
            );
        }

        return translated;
    }

    public String getTitle() {
        for(String line : this.title) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getQueuename(Queue queue) {
        for(String line : this.queuename) {
            return ChatColor.translateAlternateColorCodes('&', line
            .replace("{queue}", queue.getName()));
        }
        return null;
    }

    public String getServerstatus() {
        for(String line : this.serverstatus) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getServerplayers() {
        for(String line : this.serverplayers) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getPlayercount(Queue queue) {
        for(String line : this.playercount) {
            return ChatColor.translateAlternateColorCodes('&', line
                    .replace("{online}", String.valueOf(queue.getServerData().getOnlinePlayers()))
                    .replace("{max}", String.valueOf(queue.getServerData().getMaximumPlayers())));
        }
        return null;
    }

    public String getNoplayers() {
        for(String line : this.noplayers) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getQueuestatus() {
        for(String line : this.queuestatus) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getOnline() {
        for(String line : this.online) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getWhitelisted() {
        for(String line : this.whitelisted) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getOffline() {
        for(String line : this.offline) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }

    public String getPermission() {
        for (String line : this.permission) {
            return ChatColor.translateAlternateColorCodes('&', line);
        }
        return null;
    }
}
