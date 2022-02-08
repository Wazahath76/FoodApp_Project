package com.learning.exception;

import lombok.ToString;

//this thing is an alternate for the override function toString() below
@ToString(callSuper = true)

public class InvalidIdLengthException extends Exception {
	//IdNotFoundException refered as an user defined exception
	
	public InvalidIdLengthException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		return "IdNotFoundException [toString()=" + super.toString() + "]";
//	}
	
}
