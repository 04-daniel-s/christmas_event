package de.lecuutex.christmasEvent.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsListener implements Listener {

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        //event.setCancelled(false);
    }
}
