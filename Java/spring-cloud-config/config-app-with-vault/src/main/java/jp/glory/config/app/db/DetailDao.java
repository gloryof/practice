package jp.glory.config.app.db;

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

    public List<DetailRecord> findAll() {

        return template.query("SELECT * FROM details",
                new BeanPropertyRowMapper<>(DetailRecord.class));
    }
}
