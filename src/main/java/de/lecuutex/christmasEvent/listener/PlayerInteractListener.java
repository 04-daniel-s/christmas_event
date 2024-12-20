package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import de.lecuutex.christmasEvent.entities.ChristmasHead;
import net.nimbus.commons.Commons;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        for (Material allowedBlock : getAllowedBlocks()) {
            if (ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) continue;
            if (event.getClickedBlock() == null) continue;
            Material type = event.getClickedBlock().getType();

            if (allowedBlock != type) {
                event.setCancelled(true);
            }
        }

        if (player.getItemInHand() == null || !player.getItemInHand().hasItemMeta()) return;

        if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        if (event.getClickedBlock() == null) return;

        ItemStack item = player.getItemInHand();
        if (item.getItemMeta().getDisplayName().contains("head_update")) {
            if (event.getClickedBlock() == null) return;
            if (event.getClickedBlock().getType() != Material.SKULL) return;

            Block block = event.getClickedBlock();
            Location location = block.getLocation();

            if (!ChristmasEvent.getInstance().getHeadService().filterCache(head -> head.exists(location)).isEmpty()) {
                player.sendMessage(ChristmasEvent.PREFIX + "Der Kopf ist bereits gespeichert!");
                return;
            }

            player.sendMessage(ChristmasEvent.PREFIX + "§aDu hast einen Kopf hinzugefügt!");
            ChristmasHead head = ChristmasHead.builder().timestamp(new Date()).creator(player.getUniqueId().toString()).location(location).build();
            Commons.getInstance().getExecutorService().submit(() -> ChristmasEvent.getInstance().getHeadService().createNew(head));
        }

        if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("head_check")) {
            if (event.getClickedBlock().getType() != Material.SKULL) return;
            Location blockLoc = event.getClickedBlock().getLocation();

            for (ChristmasHead head : ChristmasEvent.getInstance().getHeadService().getCache().getAll()) {
                if (!head.exists(blockLoc)) continue;

                player.sendMessage(ChristmasEvent.PREFIX + "Hier ist ein Kopf mit der ID: " + head.getId());
            }
        }
    }

    public List<Material> getAllowedBlocks() {
        List<Material> materials = new ArrayList<>();

        materials.add(Material.STONE_BUTTON);
        materials.add(Material.SKULL);
        return materials;
    }
}
