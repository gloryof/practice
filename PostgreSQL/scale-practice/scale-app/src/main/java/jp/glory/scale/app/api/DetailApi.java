package jp.glory.scale.app.api;

import jp.glory.scale.app.db.DetailDao;
import jp.glory.scale.app.db.DetailRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detail")
public class DetailApi {

    private final DetailDao dao;

    public DetailApi(final DetailDao dao) {
        this.dao = dao;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RegisterResult> register(@RequestBody  final RegisterParam param) {

        Long id = dao.insert(param.name);

        return ResponseEntity.ok(new RegisterResult(id));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Result> getDetails() {

        List<Detail> details = dao.findAll().stream().map(Detail::new).collect(Collectors.toList());

        return ResponseEntity.ok(new Result(details));
    }

    static class RegisterParam {
        public String name;
    }

    static class RegisterResult {
        public final long id;

        RegisterResult(final long id) {
            this.id = id;
        }
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
