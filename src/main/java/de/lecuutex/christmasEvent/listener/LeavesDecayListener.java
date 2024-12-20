package de.lecuutex.christmasEvent.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayListener implements Listener {

    @EventHandler
    public void onDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }
}
