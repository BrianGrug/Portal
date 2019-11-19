package me.joeleoli.portal.bukkit.priority.impl;

import me.joeleoli.portal.bukkit.Portal;
import me.joeleoli.portal.bukkit.priority.PriorityProvider;
import me.joeleoli.portal.bukkit.util.MapUtil;
import me.joeleoli.portal.shared.queue.QueueRank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DefaultPriorityProvider implements PriorityProvider {

    private static final Portal plugin = Portal.getInstance();

    private QueueRank defaultPriority;
    private Map<String, QueueRank> priorities = new HashMap<>();

    public DefaultPriorityProvider() {
        FileConfiguration config = plugin.getMainConfig().getConfig();

        try {
            this.defaultPriority = new QueueRank("Default", 1);

            if (config.contains("priority.default")) {
                this.defaultPriority.setPriority(config.getInt("priority.default"));
            }

            if (config.contains("priority.ranks") && config.isConfigurationSection("priority.ranks")) {
                for (String rank : config.getConfigurationSection("priority.ranks").getKeys(false)) {
                    String path = "priority.ranks." + rank;

                    if (config.contains(path + ".priority") && config.contains(path + ".permission")) {
                        this.priorities.put(config.getString(path + ".permission"), new QueueRank(rank, config.getInt
                                (path + ".priority")));
                    }
                }
            }

            this.priorities = MapUtil.sortByValue(this.priorities);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to configure default priority provider.");
            e.printStackTrace();
        }
    }

    @Override
    public QueueRank getPriority(Player player) {
        for (Map.Entry<String, QueueRank> entry : this.priorities.entrySet()) {
            if (player.hasPermission(entry.getKey())) {
                return entry.getValue();
            }
        }

        return this.defaultPriority;
    }

}
