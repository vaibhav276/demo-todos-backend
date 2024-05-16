package com.demo.demotodos.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TodoDto {
	@JsonProperty("todo_id")
	private String todoId;

	@JsonProperty(value = "due_date")
	private Date dueDate;

	private String title;
	private String description;
	private Boolean done;

	public boolean hasDueDate() {
		return (dueDate != null);
	}

	public boolean hasTitle() {
		return (title != null && !title.isBlank());
	}

	public boolean hasDescription() {
		return (description != null && !description.isBlank());
	}

	public boolean hasDone() {
		return (done != null);
	}
}
