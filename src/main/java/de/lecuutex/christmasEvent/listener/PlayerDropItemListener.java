package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (!ChristmasEvent.getInstance().getCanBuild().contains(event.getPlayer().getUniqueId().toString())) return;
        event.setCancelled(true);
    }
}
