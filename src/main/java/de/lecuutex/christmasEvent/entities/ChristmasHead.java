package de.lecuutex.christmasEvent.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.nimbus.commons.database.LongIdentifierEntity;
import org.bukkit.Location;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class ChristmasHead implements LongIdentifierEntity {

    private Long id;

    private String creator;

    private Date timestamp;

    private Location location;

    public boolean exists(Location location) {
        if (this.location.getX() != location.getX()) return false;
        if (this.location.getY() != location.getY()) return false;
        if (this.location.getZ() != location.getZ()) return false;
        return true;
    }
}
