package com.prepmate.backend.exception;

public class BadRequestException extends BaseException {
	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
