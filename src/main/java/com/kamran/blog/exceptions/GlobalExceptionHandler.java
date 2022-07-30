package com.kamran.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kamran.blog.payloads.ApiResponse;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> errorResp = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errorResp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(errorResp, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiExceptionHandler(ApiException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	

}
