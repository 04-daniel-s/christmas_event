package de.lecuutex.christmasEvent.entities;

import de.lecuutex.christmasEvent.ChristmasEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.nimbus.commons.database.LongIdentifierEntity;

import java.util.Date;
import java.util.Optional;
import java.util.OptionalInt;

@Data
@Builder
@AllArgsConstructor
public class FoundHead implements LongIdentifierEntity {

    private Long id;

    private String playerUUID;

    private Date timestamp;

    private Long headId;

    public ChristmasHead getChristmasHead() {
        Optional<ChristmasHead> optional = ChristmasEvent.getInstance().getHeadService().filterCache(head -> head.getId().longValue() == headId.longValue()).stream().findFirst();
        return optional.orElse(null);
    }
}
