package me.joeleoli.portal.bukkit;

import me.joeleoli.portal.bukkit.command.commands.*;
import me.joeleoli.portal.bukkit.config.FileConfig;
import me.joeleoli.portal.bukkit.config.Language;
import me.joeleoli.portal.bukkit.jedis.PortalSubscriptionHandler;
import me.joeleoli.portal.bukkit.listener.InventoryClickListener;
import me.joeleoli.portal.bukkit.listener.PlayerListener;
import me.joeleoli.portal.bukkit.priority.PriorityProvider;
import me.joeleoli.portal.bukkit.priority.impl.DefaultPriorityProvider;
import me.joeleoli.portal.bukkit.server.Server;
import me.joeleoli.portal.bukkit.thread.ReminderThread;
import me.joeleoli.portal.bukkit.thread.UpdateThread;
import me.joeleoli.portal.bukkit.util.Menus;
import me.joeleoli.portal.shared.jedis.JedisChannel;
import me.joeleoli.portal.shared.jedis.JedisPublisher;
import me.joeleoli.portal.shared.jedis.JedisSettings;
import me.joeleoli.portal.shared.jedis.JedisSubscriber;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Portal extends JavaPlugin {

    @Getter
    private static Portal instance;

    private FileConfig mainConfig;
    private Language language;

    private JedisSettings settings;
    private JedisPublisher publisher;
    private JedisSubscriber subscriber;
    public Menus menus;

    private Server portalServer;

    @Setter
    private PriorityProvider priorityProvider;

    @Override
    public void onEnable() {
        instance = this;

        this.mainConfig = new FileConfig(this, "config.yml");

        this.language = new Language();
        this.language.load();

        this.portalServer = new Server(
                this.mainConfig.getConfig().getString("server.id"),
                this.mainConfig.getConfig().getBoolean("server.hub")
        );

        this.settings = new JedisSettings(
                this.mainConfig.getConfig().getString("redis.host"),
                this.mainConfig.getConfig().getInt("redis.port"),
                !this.mainConfig.getConfig().contains("redis.password") ? null : this.mainConfig.getConfig().getString("redis.password")
        );

        this.subscriber = new JedisSubscriber(JedisChannel.BUKKIT, this.settings, new PortalSubscriptionHandler());
        this.publisher = new JedisPublisher(this.settings);
        this.publisher.start();

        this.priorityProvider = new DefaultPriorityProvider();

        menus = new Menus();

        // Start threads
        new UpdateThread().start();
        new ReminderThread().start();

        // Register commands
        new QueueMenuCommand();
        new JoinQueueCommand();
        new LeaveQueueCommand();
        new ForceSendCommand();
        new DataDumpCommand();
        new QueueToggleCommand();
        new QueueClearCommand();
        new StatusCommand();
        if(getConfig().getBoolean("commands.hub")) {
            new HubCommand();
        }

        // Register listeners
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        // Register plugin message channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        if (this.settings.getJedisPool() != null && !this.settings.getJedisPool().isClosed()) {
            this.settings.getJedisPool().close();
        }
    }
}
