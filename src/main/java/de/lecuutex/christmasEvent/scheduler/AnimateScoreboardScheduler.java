package de.lecuutex.christmasEvent.scheduler;

import de.lecuutex.christmasEvent.ChristmasEvent;
import de.lecuutex.christmasEvent.scoreboard.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AnimateScoreboardScheduler extends AbstractScheduler {
    public AnimateScoreboardScheduler() {
        super(20, 20 * 3);
    }

    @Override
    public void run() {
        ScoreboardHandler scoreboardHandler = ChristmasEvent.getInstance().getScoreboardHandler();

        scoreboardHandler.getScoreboards().forEach((uuid, scoreboard) -> {
            String header;

            if (scoreboard.getHeader().equals("§8┃ §cWeih§fnachts§ce§fvent")) {
                header = "§8┃ §fWeih§cnachts§fe§cvent";
            } else {
                header = "§8┃ §cWeih§fnachts§ce§fvent";
            }

            scoreboard.updateLine(0, header);
            scoreboard.setHeader(header);
        });


        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            scoreboardHandler.updateScoreboard(onlinePlayer);
        }
    }
}
