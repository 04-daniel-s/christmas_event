package de.lecuutex.christmasEvent.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getWorld().getName().equals("world")) {
            if (player.getLocation().distance(new Location(Bukkit.getWorld("world"), -254, 43, 178)) <= 2) {
                player.teleport(new Location(Bukkit.getWorld("world"), -146, 205, -43));
            }
        }
    }
}
