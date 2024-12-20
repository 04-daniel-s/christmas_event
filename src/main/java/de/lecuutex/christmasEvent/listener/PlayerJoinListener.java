package de.lecuutex.christmasEvent.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setHealth(20);
        player.setFoodLevel(20);
        event.setJoinMessage("");
        player.setGameMode(GameMode.ADVENTURE);

        //TODO ADD COMPASS TO TP TO SPAWN
        //TODO TP TO SPAWN
    }
}
