package de.lecuutex.christmasEvent.services;

import de.lecuutex.christmasEvent.entities.ChristmasHead;
import de.lecuutex.christmasEvent.util.DatabaseHelper;
import net.nimbus.commons.database.OfflineService;
import net.nimbus.commons.database.query.Result;
import net.nimbus.commons.database.query.Row;

import java.util.Optional;

public class HeadService extends OfflineService<Long, ChristmasHead> {

    @Override
    protected Long insertInternal(ChristmasHead christmasHead) {
        String sql = "INSERT INTO head_locations(creator, timestamp, location) VALUES (?,?,?)";
        Object[] objects = {christmasHead.getCreator(), christmasHead.getTimestamp(), DatabaseHelper.serializeLocation(christmasHead.getLocation())};
        return sqlWrite(sql, objects);
    }

    @Override
    public void update(ChristmasHead christmasHead) {
        updateCache(christmasHead.getId(), christmasHead);
        String sql = "UPDATE head_locations SET creator = ?, timestamp = ?, location = ? WHERE id = ?";
        Object[] objects = {christmasHead.getCreator(), christmasHead.getTimestamp(), DatabaseHelper.serializeLocation(christmasHead.getLocation()), christmasHead.getId()};
        sqlWrite(sql, objects);
    }

    @Override
    public Optional<ChristmasHead> query(String s, Object... objects) {
        Result result = sqlQuery(s, objects);
        if (result.isEmpty()) return Optional.empty();
        return Optional.ofNullable(buildChristmasHead(result.getFirstRow()));
    }

    public ChristmasHead buildChristmasHead(Row row) {
        return ChristmasHead.builder()
                .id(row.getLong("id"))
                .creator(row.getString("creator"))
                .timestamp(row.getDate("timestamp"))
                .location(DatabaseHelper.deserializeLocation(row.getString("location")))
                .build();
    }
}
