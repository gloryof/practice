package jp.glory.monitoring.app.external.jdbc.user;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import jp.glory.monitoring.app.external.jdbc.Dao;

@Dao
public class UserTableDao {

    private static final String TABLE_NAME = "monitor_user";

    private static final String BASE_SQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY id ASC";
    private static final String LIMIT_SQL = BASE_SQL + " LIMIT 100";

    private final JdbcTemplate template;
    

    public UserTableDao(final JdbcTemplate template) {

        this.template = template;
    }

    public List<UserTable> findAll() {

        return template.query(BASE_SQL, createMapper());
    }

    public List<UserTable> findInLimit() {

        return template.query(LIMIT_SQL, createMapper());
    }

    private BeanPropertyRowMapper<UserTable> createMapper() {

        return new BeanPropertyRowMapper<>(UserTable.class);
    }
}
