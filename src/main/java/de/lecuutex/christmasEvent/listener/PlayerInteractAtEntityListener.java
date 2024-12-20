package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if(e.getRightClicked() instanceof Player) {
            Player player = (Player) e.getRightClicked();
            if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return;
            e.setCancelled(true);
        }
    }
}
