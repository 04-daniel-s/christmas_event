package de.lecuutex.christmasEvent.listener;

import de.lecuutex.christmasEvent.ChristmasEvent;
import de.lecuutex.christmasEvent.entities.FoundHead;
import de.lecuutex.christmasEvent.services.FoundHeadService;
import net.nimbus.commons.database.query.Result;
import net.nimbus.commons.database.query.Row;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        FoundHeadService foundHeadService = ChristmasEvent.getInstance().getFoundHeadService();

        Result result = foundHeadService.sqlQuery("SELECT * FROM found_heads WHERE player_uuid = ?", event.getUniqueId().toString());

        for (Row row : result.getRows()) {
            FoundHead foundHead = foundHeadService.buildFoundHead(row);
            foundHeadService.updateCache(foundHead.getHeadId(), foundHead);
        }

        System.out.println("EVENT §c> heads for the following player has been cached: " + event.getUniqueId().toString() + " [" + result.getRows().size() + "]");
    }
}
