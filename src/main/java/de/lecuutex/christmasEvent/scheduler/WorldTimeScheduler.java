package de.lecuutex.christmasEvent.scheduler;

import org.bukkit.Bukkit;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class WorldTimeScheduler extends AbstractScheduler {

    public WorldTimeScheduler() {
        super(0, 20 * 10);
    }

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Berlin")));
        long time = (1000 * cal.get(Calendar.HOUR_OF_DAY)) + (16 * cal.get(Calendar.MINUTE)) - 6000;

        Bukkit.getWorld("world").setTime(time);
    }
}
