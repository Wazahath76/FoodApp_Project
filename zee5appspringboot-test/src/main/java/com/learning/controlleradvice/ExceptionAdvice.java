package com.learning.controlleradvice;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.learning.exception.AlreadyExistsException;


@ControllerAdvice
public class ExceptionAdvice {
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<?> alreadyRecordExistsException(){
		HashMap<String,String> map=new HashMap<>();
		map.put("message", "Record already exists");
		return ResponseEntity.badRequest().body(map);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e){
		HashMap<String,String> map=new HashMap<>();
		map.put("message", "unknown exception"+e.getMessage());
		return ResponseEntity.badRequest().body(map);
	}
}
