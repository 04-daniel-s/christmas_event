package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Player player = (Player) event.getRightClicked();
            if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return;
            event.setCancelled(true);
        }
    }
}
