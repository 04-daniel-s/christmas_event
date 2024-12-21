package de.lecuutex.christmasEvent.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.nimbus.commons.database.LongIdentifierEntity;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class FoundHead implements LongIdentifierEntity {

    private Long id;

    private String playerUUID;

    private Date timestamp;

    private Long headId;
}
