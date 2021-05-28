package it.unibo.paserver.rest.controller.validator;

public class FieldError {
	
	private String fieldName;
    private String fieldError;
     
    

	public String getFieldError() {
		return fieldError;
	}

	public void setFieldError(String fieldError) {
		this.fieldError = fieldError;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
