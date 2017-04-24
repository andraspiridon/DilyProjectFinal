package com.dily.Mapper;

import com.dily.entities.Tag;
import com.dily.entities.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rusum on 13.04.2017.
 */
public class TagMapper implements RowMapper<Tag> {

    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setTag_id(rs.getInt("tag_id"));
        tag.setTag_name(rs.getString("tag_name"));
        return tag;
    }
}
