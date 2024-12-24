package de.lecuutex.christmasEvent.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ParticleScheduler extends AbstractScheduler {

    public ParticleScheduler() {
        super(0, 20 * 5);
    }


    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getLocation().distance(new Location(Bukkit.getWorld("world"), -254, 43, 178)) <= 50) {
                onlinePlayer.playEffect(new Location(Bukkit.getWorld("world"), -254, 43, 178), Effect.WITCH_MAGIC, 2);
            }
        }
    }
}
