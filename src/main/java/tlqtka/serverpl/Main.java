package tlqtka.serverpl;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Main extends JavaPlugin implements Listener {

    boolean cancelled = true;
    boolean Wither = false;
    boolean Warden = false;
    boolean Trade = false;

    Set<Villager> villagers = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Event();
        getLogger().info(ChatColor.AQUA + "Thirteen server survival plugin 1.0-SNAPSHOT is enabled.");
        Objects.requireNonNull(getCommand("기본템")).setExecutor(new BasicItemCmd());
        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true);
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, false);
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        }

        World firstWorld = Bukkit.getWorlds().get(0);
        Location center = new Location(firstWorld, 0.0, 0.0, 0.0);
        double size = 16384.0;
        firstWorld.getWorldBorder().setCenter(center);
        firstWorld.getWorldBorder().setSize(size);
        firstWorld.setSpawnLocation(firstWorld.getHighestBlockAt(0, 0).getLocation());

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            villagers.clear();
        }, 20L * 60L * 60L * 24L, 20L * 60L * 60L * 8L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.AQUA + "Thirteen server survival plugin 1.0-SNAPSHOT is disabled.");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTasks(this);
    }

    public void Event() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new Main(), this);
    }

    @Contract
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    Set<Villager> villagers2 = new HashSet<>();

    @EventHandler
    public void onVillagerReplenishTradeEvent(VillagerReplenishTradeEvent e) {
        if (villagers.contains((Villager) e.getEntity())) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
            villagers.add((Villager) e.getEntity());
        }
    }

    @EventHandler
    public void onEntityBreedEvent(EntityBreedEvent e) {
        EntityType entityType = e.getEntityType();
        if (entityType == EntityType.VILLAGER) {
            if (villagers2.contains((Villager) e.getEntity())) {
                e.setCancelled(true);
            } else {
                e.setCancelled(false);
                villagers2.add((Villager) e.getEntity());
            }
        }
    }

    @EventHandler
    public void onPlayerTradeEvent(PlayerTradeEvent e) {
        if (Trade == true) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }



    @EventHandler
    public void onWitherSkeletonSpawn(EntitySpawnEvent e) {
        EntityType entityType = e.getEntityType();
        if (entityType == EntityType.WITHER_SKELETON) {
            if (Wither == true) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        } else if (entityType == EntityType.WARDEN) {
            if (Warden == true) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }
}