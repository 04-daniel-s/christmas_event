package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) {
            event.setCancelled(true);
            event.setBuild(false);
        } else {
            event.setCancelled(false);
            event.setBuild(true);
        }
    }
}
