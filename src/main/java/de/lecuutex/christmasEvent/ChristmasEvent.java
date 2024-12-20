package de.lecuutex.christmasEvent;

import de.lecuutex.christmasEvent.commands.HeadCommand;
import de.lecuutex.christmasEvent.entities.ChristmasHead;
import de.lecuutex.christmasEvent.listener.*;
import de.lecuutex.christmasEvent.services.FoundHeadService;
import de.lecuutex.christmasEvent.services.HeadService;
import lombok.Getter;
import net.nimbus.commons.Commons;
import net.nimbus.commons.database.query.Result;
import net.nimbus.commons.database.query.Row;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.vehicle.VehicleCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ChristmasEvent extends JavaPlugin {

    @Getter
    private final List<String> canBuild = new ArrayList<>();

    @Getter
    private final List<String> showParticle = new ArrayList<>();

    public final static String PREFIX = "§2NimbusNET §7Ξ §7";

    @Getter
    private static ChristmasEvent instance;

    @Getter
    private HeadService headService;

    @Getter
    private FoundHeadService foundHeadService;

    @Override
    public void onEnable() {
        instance = this;

        Commons.getInstance().init();
        createTables();

        headService = new HeadService();
        foundHeadService = new FoundHeadService();

        getCommand("head").setExecutor(new HeadCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new LeavesDecayListener(), this);
        Bukkit.getPluginManager().registerEvents(new VehicleDestroyListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new VehicleDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new VehicleCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionPrimeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockIgniteListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPhysicsListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockFadeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockFormListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityBlockFormListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);

        Commons.getInstance().getExecutorService().execute(() -> {
            Result result = headService.sqlQuery("SELECT * FROM head_locations");

            for (Row row : result.getRows()) {
                ChristmasHead head = headService.buildChristmasHead(row);
                headService.updateCache(head.getId(), head);
            }

            System.out.println("§cEVENT §7> §a" + result.getRows().size() + " skulls have been loaded");
        });

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            showParticle.stream().filter(uuid -> Bukkit.getPlayer(UUID.fromString(uuid)) != null).map(uuid -> Bukkit.getPlayer(UUID.fromString(uuid))).forEach(player -> {
                List<ChristmasHead> heads = headService.getCache().getAll();
                for (ChristmasHead head : heads) {
                    Location headLocation = head.getLocation();
                    if (player.getLocation().distance(headLocation) > 50) continue;
                    player.playEffect(headLocation, Effect.HEART, 1);
                }
            });
        }, 0, 10);
    }

    @Override
    public void onDisable() {
        Bukkit.getWorld("world").save();
    }

    private void createTables() {
        String headLocations = "CREATE TABLE IF NOT EXISTS`head_locations`\n" + "(\n" + " `id`        bigint NOT NULL AUTO_INCREMENT ,\n" + " `creator`   varchar(255) NOT NULL ,\n" + " `timestamp` timestamp NOT NULL ,\n" + " `location`  longtext NOT NULL ,\n" + "\n" + "PRIMARY KEY (`id`),\n" + "KEY `FK_1` (`creator`),\n" + "CONSTRAINT `FK_23` FOREIGN KEY `FK_1` (`creator`) REFERENCES `players` (`uuid`)\n" + ");\n";
        createTable(headLocations);

        String christmasPlayers = "CREATE TABLE IF NOT EXISTS `found_heads`\n" + "(\n" + " `id`        bigint NOT NULL AUTO_INCREMENT ,\n" + " `player_uuid`      varchar(255) NOT NULL ,\n" + " `head_id`   bigint NOT NULL ,\n" + " `timestamp` timestamp NOT NULL ,\n" + "\n" + "PRIMARY KEY (`id`),\n" + "KEY `FK_1` (`uuid`),\n" + "CONSTRAINT `FK_24` FOREIGN KEY `FK_1` (`uuid`) REFERENCES `players` (`uuid`),\n" + "KEY `FK_2` (`head_id`),\n" + "CONSTRAINT `FK_25` FOREIGN KEY `FK_2` (`head_id`) REFERENCES `head_locations` (`id`)\n" + ");\n";
        createTable(christmasPlayers);
    }

    private void createTable(String sql) {
        try {
            PreparedStatement ps = Commons.getInstance().getConnection().prepareStatement(sql);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
