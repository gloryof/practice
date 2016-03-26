package jp.glory.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.glory.sample.exceptioin.OriginalException;
import jp.glory.sample.nonweb.CodeChecker;
import jp.glory.sample.web.response.SampleResonse;

@RestController
@RequestMapping("sample/{id}")
public class SampleApi {

    private final CodeChecker checker;

    @Autowired
    public SampleApi(final CodeChecker checker) {

        this.checker = checker;
    }

    @RequestMapping
    public ResponseEntity<SampleResonse> checkParameter(@PathVariable int id) {

        if (!checker.isValid(id)) {

            throw new OriginalException(id);
        }

        SampleResonse response = new SampleResonse();
        response.setNewId(id * 100);

        return new ResponseEntity<SampleResonse>(response, HttpStatus.OK);
    }
}
