package de.lecuutex.christmasEvent.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionPrimeListener implements Listener {

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event) {
        event.setCancelled(true);
    }
}

