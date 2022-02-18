package com.learning.exception.apierror;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data

public class ApiError {
	//this class should provide collective info regarding the error/errors
	
	private HttpStatus httpStatus;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss" )
	private LocalDateTime timeStamp;
	private String debugMessage;
	private String message;
	private List<ApiSubError> subErrors; //to hold validation related errors
	
	private ApiError() {
		timeStamp = LocalDateTime.now();
		
	}
	
	public ApiError(HttpStatus status) {
		this();
		this.httpStatus = status;
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.httpStatus = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	//every field validation in subErrorS
	//create a method to add this into subErrorS
	
	private void addSubError(ApiSubError apiSubError) {
		if(subErrors == null) {
			subErrors = new ArrayList<>();
		}
		subErrors.add(apiSubError);
	}
	
	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new ApiValidationError(object, field, rejectedValue, message));
	}
	
	private void addValidationError(String object, String messsge) {
		addSubError(new ApiValidationError(object, messsge));
	}
	
	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());;
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		//fieldErrors.forEach(this::addValidationError);
		fieldErrors.forEach(e->this.addValidationError(e));
	}
	
	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
	
	public void addValidationError1(List<ObjectError> globalErrors) {
		globalErrors.forEach(e->this.addValidationError(e));
	}
	
	public void addValidationError(ConstraintViolation<?> constraintViolation) {
		this.addValidationError(constraintViolation.getRootBeanClass().getName(), 
				((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), 
				constraintViolation.getInvalidValue(), 
				constraintViolation.getMessage());
	}
	
	public void addValidationError2(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(e->addValidationError(e));
	}
	
}