package de.lecuutex.christmasEvent.scheduler;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class UpdateSeekerScheduler extends AbstractScheduler {
    public UpdateSeekerScheduler() {
        super(20, 20 * 10);
    }

    @Override
    public void run() {
        ListenableFuture<HashMap<String, Integer>> listenableFuture = ChristmasEvent.getInstance().getScoreboardHandler().queryTopSeeker();
        Futures.addCallback(listenableFuture, new FutureCallback<>() {

            @Override
            public void onSuccess(HashMap<String, Integer> result) {
                ChristmasEvent.getInstance().getScoreboardHandler().setTopSeeker(result);

                Bukkit.getScheduler().scheduleSyncDelayedTask(ChristmasEvent.getInstance(), () -> {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        ChristmasEvent.getInstance().getScoreboardHandler().updateScoreboard(onlinePlayer);
                    }
                }, 1);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("COULD NOT LOAD TOP 5 SEEKER");
            }
        });
    }
}
