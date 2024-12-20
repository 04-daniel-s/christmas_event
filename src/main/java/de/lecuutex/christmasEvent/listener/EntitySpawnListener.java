package de.lecuutex.christmasEvent.listener;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Player) return;
        if (e.getEntity() instanceof ArmorStand) return;
        if (e.getEntity() instanceof Boat) return;
        if (e.getEntity() instanceof Minecart) return;

        e.setCancelled(true);
    }
}
