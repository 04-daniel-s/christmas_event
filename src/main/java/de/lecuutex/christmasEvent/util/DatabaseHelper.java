package de.lecuutex.christmasEvent.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class DatabaseHelper {
    public static String serializeLocation(Location location) {
        StringBuilder builder = new StringBuilder();
        builder.append("world:" + location.getWorld().getName() + ",");
        builder.append("x:" + location.getX() + ",");
        builder.append("y:" + location.getY() + ",");
        builder.append("z:" + location.getZ());
        return builder.toString();
    }

    public static Location deserializeLocation(String location) {
        String[] split = location.split(",");
        String worldName = split[0].split(":")[1];

        double x = Double.parseDouble(split[1].split(":")[1]);
        double y = Double.parseDouble(split[2].split(":")[1]);
        double z = Double.parseDouble(split[3].split(":")[1]);

        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}
