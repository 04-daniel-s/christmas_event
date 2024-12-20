package de.lecuutex.christmasEvent.services;

import de.lecuutex.christmasEvent.entities.FoundHead;
import net.nimbus.commons.database.OfflineService;
import net.nimbus.commons.database.query.Result;
import net.nimbus.commons.database.query.Row;

import java.util.Optional;

public class FoundHeadService extends OfflineService<Long, FoundHead> {

    @Override
    protected Long insertInternal(FoundHead foundHead) {
        String sql = "INSERT INTO found_heads(player_uuid, head_id, timestamp) VALUES (?,?,?)";
        Object[] objects = {foundHead.getPlayerUUID(), foundHead.getHeadId(), foundHead.getTimestamp()};
        return sqlWrite(sql, objects);
    }

    @Override
    public void update(FoundHead foundHead) {
        String sql = "UPDATE found_heads SET player_uuid = ?, head_id = ?, timestamp = ? WHERE id = ?";
        Object[] objects = {foundHead.getPlayerUUID(), foundHead.getHeadId(), foundHead.getTimestamp(), foundHead.getId()};
        sqlWrite(sql, objects);
    }

    @Override
    public Optional<FoundHead> query(String s, Object... objects) {
        Result result = sqlQuery(s, objects);
        if (result.isEmpty()) return Optional.empty();
        return Optional.ofNullable(buildFoundHead(result.getFirstRow()));
    }

    public FoundHead buildFoundHead(Row firstRow) {
    return FoundHead.builder()
            .id(firstRow.getLong("id"))
            .headId(firstRow.getLong("head_id"))
            .timestamp(firstRow.getDate("timestamp"))
            .playerUUID(firstRow.getString("player_uuid"))
            .build();
    }
}
