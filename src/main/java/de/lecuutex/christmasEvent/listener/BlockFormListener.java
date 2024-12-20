package de.lecuutex.christmasEvent.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event) {
        event.setCancelled(true);
    }
}
