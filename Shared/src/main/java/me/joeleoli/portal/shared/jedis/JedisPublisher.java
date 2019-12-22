package me.joeleoli.portal.shared.jedis;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import redis.clients.jedis.Jedis;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@RequiredArgsConstructor
public class JedisPublisher extends Thread {

	private final JedisSettings jedisSettings;
	private Queue<JedisQueue> queue = new LinkedList<>();

	@Override
	public void run() {
		while (true) {
			if (!this.queue.isEmpty()) {

				try (Jedis jedis = this.jedisSettings.getJedisPool().getResource()) {

					if (this.jedisSettings.hasPassword()) {
						jedis.auth(this.jedisSettings.getPassword());
					}

					while (!this.queue.isEmpty()) {
						JedisQueue queue = this.queue.poll();

						JsonObject json = new JsonObject();
						json.addProperty("action", queue.getAction().name());
						json.add("data", queue.getData());

						jedis.publish(queue.getChannel(), json.toString());
					}
				}
			}

			try {
				Thread.sleep(50L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void write(String channel, JedisAction action, JsonObject data) {
		this.queue.add(new JedisQueue(channel, action, data));
	}

}