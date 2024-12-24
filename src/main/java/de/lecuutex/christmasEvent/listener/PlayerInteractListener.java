package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import de.lecuutex.christmasEvent.entities.ChristmasHead;
import de.lecuutex.christmasEvent.entities.FoundHead;
import net.nimbus.commons.Commons;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            event.setCancelled(true);
        }

        Player player = event.getPlayer();

        if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.FIREWORK_CHARGE) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

            player.teleport(new Location(Bukkit.getWorld("world"), -0.5, 67, -0.5));

        } else if (ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) {
            if (player.getItemInHand() == null) return;
            ItemStack item = player.getItemInHand();

            if (item.getItemMeta() == null) return;
            if (item.getItemMeta().getDisplayName() == null) return;
            if (event.getClickedBlock().getType() != Material.SKULL) return;

            if (item.getItemMeta().getDisplayName().contains("head_update")) {
                Block block = event.getClickedBlock();
                Location location = block.getLocation();

                if (!ChristmasEvent.getInstance().getHeadService().filterCache(head -> head.exists(location)).isEmpty()) {
                    player.sendMessage(ChristmasEvent.PREFIX + "Der Kopf ist bereits gespeichert!");
                    return;
                }

                player.sendMessage(ChristmasEvent.PREFIX + "§aDu hast einen Kopf hinzugefügt!");
                ChristmasHead head = ChristmasHead.builder().timestamp(new Date()).creator(player.getUniqueId().toString()).location(location).build();
                Commons.getInstance().getExecutorService().submit(() -> ChristmasEvent.getInstance().getHeadService().createNew(head));

            } else if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("head_check")) {
                Location blockLoc = event.getClickedBlock().getLocation();

                for (ChristmasHead head : ChristmasEvent.getInstance().getHeadService().getCache().getAll()) {
                    if (!head.exists(blockLoc)) continue;

                    player.sendMessage(ChristmasEvent.PREFIX + "Hier ist ein Kopf mit der ID: " + head.getId());
                }
            }
            return;
        }

        if (event.getClickedBlock() == null) return;

        Block block = event.getClickedBlock();

        if (block.getType() == Material.SKULL) {

            Optional<ChristmasHead> optional = ChristmasEvent.getInstance().getHeadService().filterCache(head -> head.exists(block.getLocation())).stream().findFirst();
            if (!optional.isPresent()) return;

            ChristmasHead clickedHead = optional.get();
            Optional<FoundHead> optionalFoundHead = ChristmasEvent.getInstance().getFoundHeadService().filterCache(foundHead -> foundHead.getHeadId().equals(clickedHead.getId()) && foundHead.getPlayerUUID().equals(player.getUniqueId().toString())).stream().findFirst();

            if (optionalFoundHead.isEmpty()) {
                FoundHead foundHead = FoundHead.builder().playerUUID(player.getUniqueId().toString()).headId(clickedHead.getId()).timestamp(new Date()).build();
                ChristmasEvent.getInstance().getFoundHeadService().createNew(foundHead);

                player.sendMessage(ChristmasEvent.PREFIX + "Du hast einen versteckten Kopf gefunden!");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

                Bukkit.getScheduler().scheduleSyncDelayedTask(ChristmasEvent.getInstance(), () -> {
                    int foundHeadcount = ChristmasEvent.getInstance().getFoundHeadService().filterCache(head -> head.getPlayerUUID().equals(player.getUniqueId().toString())).size();
                    int overallCount = ChristmasEvent.getInstance().getHeadService().getCache().getAll().size();

                    if (foundHeadcount == overallCount) {
                        player.sendMessage(ChristmasEvent.PREFIX + "§aHerzlichen Glückwunsch, du hast §calle Köpfe §agefunden!");
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            p.sendMessage(ChristmasEvent.PREFIX + "§b" + player.getName() + " §chat alle Köpfe gefunden!");
                            p.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
                        });
                    }
                }, 20);
            } else {
                player.sendMessage(ChristmasEvent.PREFIX + "Du hast den Kopf bereits gefunden!");
            }

            return;
        }

        for (Material allowedBlock : getAllowedBlocks()) {
            if (ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) continue;
            Material type = event.getClickedBlock().getType();

            if (allowedBlock != type) {
                event.setCancelled(true);
            }
        }
    }

    public List<Material> getAllowedBlocks() {
        List<Material> materials = new ArrayList<>();

        materials.add(Material.STONE_BUTTON);
        return materials;
    }
}
