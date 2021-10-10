package co.com.mutant.controller;

import javax.validation.Valid;

import co.com.mutant.model.Response;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import co.com.mutant.model.BusinessException;
import co.com.mutant.model.Errors;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import co.com.mutant.model.Human;


@RequestMapping(value = "${base.path}")
public interface Operation{
	  @ApiOperation(
	            value = "Verify Mutant",
	            nickname = "Verify Mutant",
	            notes = "Operation to verify if a Human is a dependent mutant in its DNA sequence.",
	            response = Response.class,
	            tags = { })
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "Verify Mutant", response = Response.class),
	            @ApiResponse(code = 403, message = "Invalid Input. please put correct request", response = Errors.class),
	            @ApiResponse(code = 500, message = "Internal Server Error", response = Errors.class) })
	    @PostMapping(value = "${verify.mutant.path}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
				consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	    <T> ResponseEntity<T> verifyMutant(@ApiParam(value = "body", required = true) @Valid @RequestBody  Human body) throws BusinessException;
	  
	  
	  
	  @ApiOperation(
	            value = "get statistics",
	            nickname = "get statistics",
	            notes = "get statistics of mutants and humans",
	            response = Response.class,
	            tags = { })
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, message = "get product success", response = Response.class),
	            @ApiResponse(code = 403, message = "Invalid Input. please put correct request", response = Errors.class),
	            @ApiResponse(code = 500, message = "Internal Server Error", response = Errors.class) })
	    @GetMapping(value = "${stats.path}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
				consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	    <T> ResponseEntity<T> stats() throws BusinessException;

}
