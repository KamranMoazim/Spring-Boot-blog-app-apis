package com.kamran.blog.exceptions;


//import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Data
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	long fieldValue;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
