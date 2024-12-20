package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class VehicleCreateListener implements Listener {
    @EventHandler
    public void onVehicleCreate(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();
            if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return;
            event.setCancelled(true);
        }
    }
}
