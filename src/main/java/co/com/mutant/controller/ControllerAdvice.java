package co.com.mutant.controller;

import co.com.mutant.model.ErrorDetail;
import co.com.mutant.model.Errors;
import co.com.mutant.model.Meta;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestControllerAdvice
public class ControllerAdvice {
    @Autowired
    private HttpHeaders httpHeaders;

    private static final String MESSAGE_NOT_READADLE_EXCEPTION = "have an actual syntax error in your JSON";
    private static final String MESSAGE_METHOD_NOT_SUPPORTED_EXCEPTION = "Method %s not supported";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleMethodArgumentNotValidException(HttpServletRequestImpl httpServletRequest, MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(buildErrors(e, httpServletRequest.getRequestURI(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Errors> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequestImpl httpServletRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(buildErrors(e, httpServletRequest.getRequestURI(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Errors> handleHttpMessageNotReadableException(HttpServletRequestImpl httpServletRequest, HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(buildErrors(e, httpServletRequest.getRequestURI(), MESSAGE_NOT_READADLE_EXCEPTION));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Errors> handleHttpRequestMethodNotSupportedException(HttpServletRequestImpl httpServletRequest, HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(buildErrors(e, httpServletRequest.getRequestURI(), String.format(MESSAGE_METHOD_NOT_SUPPORTED_EXCEPTION, e.getMethod())));
    }

    private Errors buildErrors(Exception e, String uri, String detail) {
        Errors errors = new Errors();
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorDetail.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorDetail.setId(UUID.randomUUID().toString());
        errorDetail.setDetail(detail);
        errorDetail.setSource(uri);
        errorDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errors.addErrorsItem(errorDetail);

        if (e instanceof MethodArgumentNotValidException) {
            Meta meta = new Meta();
            List<String> fields = new ArrayList<>();
            ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().forEach(x ->
                    fields.add(x.getField().concat(" -> ").concat(x.getDefaultMessage() != null ? x.getDefaultMessage() : ""))
            );
            meta.setFields(fields);
            errorDetail.setMeta(meta);
        }
        return errors;
    }
}
