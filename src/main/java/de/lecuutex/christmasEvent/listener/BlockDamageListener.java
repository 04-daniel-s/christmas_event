package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamageListener implements Listener {

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return;
        event.setCancelled(true);
    }
}
