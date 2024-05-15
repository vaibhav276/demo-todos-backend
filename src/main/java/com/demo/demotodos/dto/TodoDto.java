package com.demo.demotodos.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TodoDto {
	private Timestamp due;
	private String title;
	private String description;
	private boolean done;
}
