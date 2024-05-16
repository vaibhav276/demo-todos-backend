package com.demo.demotodos.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponseDto {
	private String apiPath;
	private Integer errorCode;
	private String message;
	private Date timestamp;
}
