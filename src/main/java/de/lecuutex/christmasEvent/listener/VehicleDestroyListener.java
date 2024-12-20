package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class VehicleDestroyListener implements Listener {

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if(event.getAttacker() instanceof Player) {
            Player player = (Player) event.getAttacker();
            if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return;
            event.setCancelled(true);
        }
    }
}
