package com.mila.financialproject.api.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private T date;
	private List<String> errors;

	public Response() {
	}

	public T getDate() {
		return date;
	}

	public void setDate(T date) {
		this.date = date;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
