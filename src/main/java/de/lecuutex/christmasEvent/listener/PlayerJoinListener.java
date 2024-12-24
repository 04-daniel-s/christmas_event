package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import de.lecuutex.christmasEvent.entities.FoundHead;
import de.lecuutex.christmasEvent.services.FoundHeadService;
import net.nimbus.commons.Commons;
import net.nimbus.commons.database.query.Result;
import net.nimbus.commons.database.query.Row;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setHealth(20);
        player.setFoodLevel(20);
        event.setJoinMessage("");
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(Bukkit.getWorld("world"), -0.5, 66, -0.5));
        player.getInventory().clear();

        ItemStack spawn = new ItemStack(Material.FIREWORK_CHARGE);
        ItemMeta meta = spawn.getItemMeta();
        meta.setDisplayName("§fZum §cSpawn §fteleportieren");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        spawn.setItemMeta(meta);
        player.getInventory().setItem(4, spawn);

        Commons.getInstance().getExecutorService().execute(() -> {
            FoundHeadService foundHeadService = ChristmasEvent.getInstance().getFoundHeadService();

            Result result = foundHeadService.sqlQuery("SELECT * FROM found_heads WHERE player_uuid = ?", player.getUniqueId().toString());

            for (Row row : result.getRows()) {
                FoundHead foundHead = foundHeadService.buildFoundHead(row);
                foundHeadService.updateCache(foundHead.getId(), foundHead);
            }

            System.out.println("EVENT | heads for the following player has been cached: " + player.getUniqueId().toString() + " [" + result.getRows().size() + "]");
            System.out.println("CURRENTLY CACHED: " + foundHeadService.filterCache(head -> head.getPlayerUUID().equals(player.getUniqueId().toString())).size());
            foundHeadService.getCache().getAll().forEach(System.out::println);
        });
    }
}
