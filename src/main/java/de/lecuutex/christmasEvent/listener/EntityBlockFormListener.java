package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockFormListener implements Listener {

    @EventHandler
    public void onEntityBlockFormEvent(EntityBlockFormEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!ChristmasEvent.getInstance().getCanBuild().contains(player.getUniqueId().toString())) return;
            event.setCancelled(true);
        }
    }
}
