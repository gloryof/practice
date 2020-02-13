package jp.glory.config.app.api;

import jp.glory.config.app.db.DetailDao;
import jp.glory.config.app.db.DetailRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/db")
public class DbApi {

    private final DetailDao dao;

    public DbApi(final DetailDao dao) {
        this.dao = dao;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Result> getDetails() {

        List<Detail> details = dao.findAll().stream().map(Detail::new).collect(Collectors.toList());

        return ResponseEntity.ok(new Result(details));
    }

    static class Result {
        public final List<Detail> details;

        Result(final List<Detail> details) {

            this.details = details;
        }
    }

    static class Detail {
        public final long id;
        public final String name;

        Detail(final DetailRecord record) {
            this.id = record.getId();
            this.name = record.getName();
        }
    }
}
