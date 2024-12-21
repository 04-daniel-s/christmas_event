package de.lecuutex.christmasEvent.scheduler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.lecuutex.christmasEvent.ChristmasEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionbarScheduler extends AbstractScheduler {

    public ActionbarScheduler() {
        super(0, 1);
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            int foundHeadsSize = ChristmasEvent.getInstance().getFoundHeadService().filterCache(head -> head.getPlayerUUID().equals(onlinePlayer.getUniqueId().toString())).size();
            int allHeads = ChristmasEvent.getInstance().getHeadService().getCache().getAll().size();

            sendActionbar(onlinePlayer, "§f● §cDu hast §a" + foundHeadsSize + "§c von §2" + allHeads + "§c Köpfen gefunden §f●");
        }
    }

    public void sendActionbar(Player player, String s) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.CHAT);
        packetContainer.getBytes().write(0, (byte) 2);
        packetContainer.getChatComponents().write(0, WrappedChatComponent.fromText(s));

        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
    }
}
