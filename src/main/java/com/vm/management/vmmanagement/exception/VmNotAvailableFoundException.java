package com.vm.management.vmmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VmNotAvailableFoundException extends Exception {

	public VmNotAvailableFoundException(String message) {
		super(message);
	}
}
