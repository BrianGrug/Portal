package me.joeleoli.portal.bukkit.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.joeleoli.portal.bukkit.Portal;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

public final class BungeeUtil {

	private BungeeUtil() {
		throw new RuntimeException("Cannot instantiate a utility class.");
	}

	public static void sendToServer(Player player, String server) {
		Validate.notNull(player, server, "Input values cannot be null!");

		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF(server);

			player.sendPluginMessage(Portal.getInstance(), "BungeeCord", out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
