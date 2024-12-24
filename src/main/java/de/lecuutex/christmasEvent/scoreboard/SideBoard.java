package de.lecuutex.christmasEvent.scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import de.lecuutex.christmasEvent.ChristmasEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.*;

public class SideBoard {

    @Getter
    private final Player player;

    @Getter
    private final HashMap<Integer, String> lines = new HashMap<>();

    @Getter
    @Setter
    private String header = "§8┃ §cWeih§fnachts§ce§fvent";

    public SideBoard(Player player) {
        this.player = player;
        showScoreboard();
    }

    private void showScoreboard() {
        createScoreboardObjective();
        displayScoreboard();
        updateScoreboard();
    }

    public void updateScoreboard() {
        setDefaultLines();
    }

    private void setDefaultLines() {
        List<String> defaultLines = new ArrayList<>();

        defaultLines.add(header);
        defaultLines.add(" ");

        HashMap<String, Integer> topSeeker = ChristmasEvent.getInstance().getScoreboardHandler().getTopSeeker();

        topSeeker.forEach((name, amount) -> {
            String line = "§7└ §d" + name + " §c[§f" + amount + "§c]";
            defaultLines.add(line);
        });

        for (int i = 0; i < 5 - topSeeker.size(); i++) {
            StringBuilder builder = new StringBuilder("§7└ §7Nicht belegt");

            for (int j = 0; j < i; j++) {
                builder.append(" ");
            }

            defaultLines.add(builder.toString());
        }

        defaultLines.add("  ");
        defaultLines.add("§9Discord:");
        defaultLines.add("§7└ §fdc.brigadia.net");
        defaultLines.add("   ");
        defaultLines.add("§dGewinn:");
        defaultLines.add("§7└ §cPlatz 1:§f 50€");

        Collections.reverse(defaultLines);

        for (int i = 0; i < defaultLines.size(); i++) {
            updateLine(i, defaultLines.get(i));
        }
    }

    private void createScoreboardObjective() {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);

        packetContainer.getStrings().write(0, "board").write(1, "§d§lBrigadia");
        packetContainer.getEnumModifier(ObjectiveType.class, 2).write(0, ObjectiveType.INTEGER);
        packetContainer.getIntegers().write(0, 0);

        sendPacket(packetContainer);
    }

    private void displayScoreboard() {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
        packetContainer.getIntegers().write(0, 1);
        packetContainer.getStrings().write(0, "board");

        sendPacket(packetContainer);
    }

    private void setLine(int line, String content) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        packetContainer.getStrings().write(0, content).write(1, "board");
        packetContainer.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.CHANGE);
        packetContainer.getIntegers().write(0, line);

        lines.put(line, content);
        sendPacket(packetContainer);
    }

    private void deleteLine(int line) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        packetContainer.getStrings().write(0, lines.get(line)).write(1, "board");
        packetContainer.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.REMOVE);

        lines.remove(line);
        sendPacket(packetContainer);
    }

    public void updateLine(int line, String content) {
        if (lines.containsKey(line)) deleteLine(line);
        setLine(line, content);
    }


    private void sendPacket(PacketContainer packetContainer) {
        ProtocolLibrary.getProtocolManager().sendServerPacket(getPlayer(), packetContainer);
    }

    private enum ObjectiveType {
        INTEGER;
    }
}
