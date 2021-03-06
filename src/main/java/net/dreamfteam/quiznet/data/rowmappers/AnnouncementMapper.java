package net.dreamfteam.quiznet.data.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import net.dreamfteam.quiznet.data.entities.Announcement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AnnouncementMapper implements RowMapper<Announcement> {
    @Override
    public Announcement mapRow(ResultSet resultSet, int i) throws SQLException {


        Announcement announcement = Announcement.builder()
                .announcementId(resultSet.getString("announcement_id"))
                .creatorId(resultSet.getString("username"))
                .title(resultSet.getString("title"))
                .textContent(resultSet.getString("text_content"))
                .image(resultSet.getBytes("image"))
                .creationDate(resultSet.getTimestamp("datetime_creation"))
                .isPublished(resultSet.getBoolean("is_published"))
                .publicationDate(resultSet.getTimestamp("datetime_publication"))
                .build();

        return announcement;
    }
}
