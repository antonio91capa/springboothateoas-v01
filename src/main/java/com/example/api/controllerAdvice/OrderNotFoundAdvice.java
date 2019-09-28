package com.example.api.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.api.exception.OrderNotFoundException;

@ControllerAdvice
public class OrderNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String orderNotFoundHandler(OrderNotFoundException e) {
		return e.getMessage();
	}

}
