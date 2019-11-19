package me.joeleoli.portal.bukkit.priority;

import me.joeleoli.portal.shared.queue.QueueRank;
import org.bukkit.entity.Player;

public interface PriorityProvider {

    QueueRank getPriority(Player player);

}
