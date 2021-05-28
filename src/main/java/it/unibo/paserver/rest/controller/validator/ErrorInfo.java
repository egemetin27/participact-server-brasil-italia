package it.unibo.paserver.rest.controller.validator;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
	private String url;
    private String message;
    private List<FieldError> fieldErrors;
     
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public ErrorInfo() {
    	fieldErrors =  new ArrayList< FieldError >();
    }
     
    

}
