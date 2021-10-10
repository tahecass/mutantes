package co.com.mutant.util;

import co.com.mutant.model.ErrorDetail;
import co.com.mutant.model.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public class ResponseCreator {

	
	public static ResponseEntity<Errors> createErrorResponse(String uri, String message, HttpStatus httpStatus,
															 Exception exception) {
		Errors errors = new Errors();
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setCode(String.valueOf(httpStatus.value()));
		errorDetail.setStatus(String.valueOf(httpStatus.value()));
		errorDetail.setId(UUID.randomUUID().toString());
		errorDetail.setDetail(message);
		errorDetail.setSource(uri);
		errorDetail.setTitle(httpStatus.getReasonPhrase());
		errors.addErrorsItem(errorDetail);
		ResponseEntity<Errors> response = new ResponseEntity<Errors>(errors, httpStatus);
		
		return response;
	}

}
