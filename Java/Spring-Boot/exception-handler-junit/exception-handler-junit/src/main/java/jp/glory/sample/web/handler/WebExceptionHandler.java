package jp.glory.sample.web.handler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.glory.sample.exceptioin.OriginalException;
import jp.glory.sample.web.response.ErrorResponse;

@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(OriginalException.class)
    public ResponseEntity<ErrorResponse> handleOriginalException(OriginalException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage("Throw OriginalException!!");

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ErrorResponse response = new ErrorResponse();
        response.setMessage("Throw TypeMismatchException!!");

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }
}
