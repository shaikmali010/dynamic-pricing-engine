package com.shaik.dynamicpricing.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
//	===================================================================================================
	private ErrorResponse buildResponse(HttpStatus status, 
			String message, HttpServletRequest request) {
		
		return ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(message)
				.path(request.getRequestURI())
				.build();
	}

//	====================================================================================================
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoun(
			ProductNotFoundException ex, HttpServletRequest request){
		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request));
	}
	
//	======================================================================================================
	@ExceptionHandler(DuplicateProductException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateProduct(
			DuplicateProductException ex, HttpServletRequest request){
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request));
	}
	
//	======================================================================================================
	@ExceptionHandler(DuplicatePricingRuleException.class)
	public ResponseEntity<ErrorResponse> handleDuplicatePricing(DuplicatePricingRuleException ex, 
			HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request));
	}
	
//	=======================================================================================================
	@ExceptionHandler(PricingRuleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePricingRuleNotFound(PricingRuleNotFoundException ex,
			HttpServletRequest request){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request));
	}
	
//	=======================================================================================================
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleFieldValidation(MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult()
		  .getFieldErrors()
		  .forEach(error -> {
			  errors.put(error.getField(), error.getDefaultMessage());
		  });
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
//	======================================================================================================
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerException(Exception ex, HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request));
	}
	
	
	
}
