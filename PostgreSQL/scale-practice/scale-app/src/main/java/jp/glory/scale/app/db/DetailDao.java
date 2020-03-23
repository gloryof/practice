package jp.glory.scale.app.db;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DetailDao {

    private final JdbcTemplate template;

    public DetailDao(final JdbcTemplate template) {
        this.template = template;
    }

    public long insert(final String name) {

        String query = "INSERT INTO details(id, name) VALUES(nextval('id_seq'), ?) RETURNING id";
        return template.queryForObject(query, Long.class, name);
    }

    public List<DetailRecord> findAll() {

        return template.query("SELECT * FROM details",
                new BeanPropertyRowMapper<>(DetailRecord.class));
    }
}
