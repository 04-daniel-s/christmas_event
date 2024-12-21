package de.lecuutex.christmasEvent.scheduler;

import de.lecuutex.christmasEvent.ChristmasEvent;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public abstract class AbstractScheduler {

    private int id;

    private long delay;

    private long cycle;

    public AbstractScheduler(long delay, long cycle) {
        this.delay = delay;
        this.cycle = cycle;
    }

    public void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(ChristmasEvent.getInstance(), this::run, delay, cycle);
    }

    public abstract void run();
}
