package de.lecuutex.christmasEvent.scoreboard;

import com.google.common.util.concurrent.ListenableFuture;
import de.lecuutex.christmasEvent.ChristmasEvent;
import lombok.Getter;
import lombok.Setter;
import net.nimbus.commons.Commons;
import net.nimbus.commons.database.query.Result;
import net.nimbus.commons.database.query.Row;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ScoreboardHandler {

    @Getter
    @Setter
    private HashMap<String, Integer> topSeeker = new HashMap<>();

    @Getter
    private final HashMap<String, SideBoard> scoreboards = new HashMap<>();

    public void createScoreboard(Player player) {
        if (scoreboards.containsKey(player.getUniqueId().toString())) return;
        SideBoard sideBoard = new SideBoard(player);

        scoreboards.put(player.getUniqueId().toString(), sideBoard);
    }

    public void updateScoreboard(Player player) {
        if (!scoreboards.containsKey(player.getUniqueId().toString())) return;
        SideBoard sideBoard = scoreboards.get(player.getUniqueId().toString());
        sideBoard.updateScoreboard();
    }

    public void removeScoreboard(Player player) {
        if (!scoreboards.containsKey(player.getUniqueId().toString())) return;
        scoreboards.remove(player.getUniqueId().toString());
    }

    public ListenableFuture<HashMap<String, Integer>> queryTopSeeker() {
        return Commons.getInstance().getExecutorService().submit(() -> {
            HashMap<String, Integer> topSeeker = new HashMap<>();

            Result topResult = ChristmasEvent.getInstance().getFoundHeadService().sqlQuery("SELECT player_uuid, COUNT(*) as amount FROM found_heads GROUP BY player_uuid ORDER BY amount DESC LIMIT 5");

            for (Row row : topResult.getRows()) {
                String playerName = Commons.getInstance().getNimbusPlayerService().getPlayerName(row.getString("player_uuid"));
                topSeeker.put(playerName, row.getInteger("amount"));
            }

            return topSeeker;
        });
    }
}
