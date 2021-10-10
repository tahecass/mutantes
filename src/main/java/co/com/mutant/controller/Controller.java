package co.com.mutant.controller;

import co.com.mutant.model.Human;
import co.com.mutant.service.Service;
import co.com.mutant.util.ResponseCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoCommandException;

import co.com.mutant.model.BusinessException;

@RestController
public class Controller implements Operation {

    @Autowired
    Service service;

    @Override
    public <T> ResponseEntity<T> verifyMutant(Human body) {
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = service.verifyMutant(body);
        } catch (MongoCommandException e) {
            responseEntity = ResponseCreator.createErrorResponse("/mutant", e.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (Exception e) {
            responseEntity = ResponseCreator.createErrorResponse("/mutant", e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        return (ResponseEntity<T>) responseEntity;
    }

    @Override
    public <T> ResponseEntity<T> stats() {
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = service.getStats();
        } catch (MongoCommandException e) {
            responseEntity = ResponseCreator.createErrorResponse("/stats", e.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);

        } catch (BusinessException e) {
            responseEntity = ResponseCreator.createErrorResponse("/stats", e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        } catch (Exception e) {
            responseEntity = ResponseCreator.createErrorResponse("/stats", e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        return (ResponseEntity<T>) responseEntity;
    }

}
