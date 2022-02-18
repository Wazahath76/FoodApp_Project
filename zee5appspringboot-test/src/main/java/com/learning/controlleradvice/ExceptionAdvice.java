package com.learning.controlleradvice;

import java.net.http.HttpHeaders;
import java.util.HashMap;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.exception.apierror.ApiError;

import net.bytebuddy.description.modifier.MethodArguments;

@ControllerAdvice
//public class ExceptionAdvice extends ResponseEntityExceptionHandler 
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
	//this class should be used when user-defined exception is called throughout all the controllers
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<?> alreadyRecordExistsExceptionHandler(AlreadyExistsException e){
		HashMap<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e){
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "unknown exception"+" "+e.getMessage());
		return ResponseEntity.badRequest().body(map);
		
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<?> idNotFoundExceptionHandler(IdNotFoundException e){
		HashMap<String, String> map = new HashMap<>();
		map.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
	
//	@Valid should be customized
//	custom error details should with subfield errors
//	create beans
//	prepare list and methods
    
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getBindingResult().getFieldErrors()); //field wise errors
		apiError.addValidationError1(ex.getBindingResult().getGlobalErrors());
		return buiResponseEntity(apiError);
	}
	
	//this will help to get the response entity object
	//if i want to mke any changes into our existing object then in every return we have to do the change
	//instead if we use this method --> we can have ease of maintanance
	private ResponseEntity<Object> buiResponseEntity(ApiError apiError){
		return new ResponseEntity<>(apiError, apiError.getHttpStatus());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolation() {
		return null;
	}
}