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

}
