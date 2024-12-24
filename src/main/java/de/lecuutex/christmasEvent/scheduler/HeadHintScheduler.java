package de.lecuutex.christmasEvent.scheduler;

import de.lecuutex.christmasEvent.ChristmasEvent;
import de.lecuutex.christmasEvent.entities.ChristmasHead;
import de.lecuutex.christmasEvent.entities.FoundHead;
import net.nimbus.commons.Commons;
import net.nimbus.commons.entities.NimbusPlayer;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeadHintScheduler extends AbstractScheduler {
    public HeadHintScheduler() {
        super(20 * 60, 20 * 60);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Optional<NimbusPlayer> optional = Commons.getInstance().getNimbusPlayerService().get(player.getUniqueId().toString());

            if (optional.isEmpty()) {
                player.kickPlayer("Es ist ein Fehler aufgetreten!");
                return;
            }

            NimbusPlayer nimbusPlayer = optional.get();
            if (nimbusPlayer.hasPermission("christmasevent.hint")) {
                List<ChristmasHead> heads = ChristmasEvent.getInstance().getHeadService().filterCache(head -> player.getLocation().distance(head.getLocation()) <= 25);
                List<FoundHead> foundHeads = ChristmasEvent.getInstance().getFoundHeadService().filterCache(head -> head.getPlayerUUID().equals(player.getUniqueId().toString()));
                int nearbyHeads = heads.stream().filter(head -> !foundHeads.stream().map(found -> found.getHeadId()).collect(Collectors.toList()).contains(head.getId())).collect(Collectors.toList()).size();

                if (nearbyHeads == 1) {
                    player.sendMessage(ChristmasEvent.PREFIX + "§cIm Radius von §f25 Blöcken §cist §fein §cKopf versteckt!");
                    return;
                }

                player.sendMessage(ChristmasEvent.PREFIX + "§cIm Radius von §f25 Blöcken §csind §f" + nearbyHeads + " §cKöpfe versteckt!");
            }
        });
    }
}
