package com.demo.demotodos.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TodoDto {
	@JsonProperty("todo_id")
	private String todoId;

	@FutureOrPresent(message = "due_date cannot be in the past")
	@NotNull(message = "due_date cannot be null")
	@JsonProperty(value = "due_date")
	private Date dueDate;

	@NotBlank(message = "title cannot be empty or null")
	@Size(max = 128, message = "title cannot be more than 128 characters long")
	private String title;

	@Size(max = 1024, message = "description cannot be more than 1Kb")
	private String description;
	private Boolean done;

	// Following functions are used in enabling
	// a partial Dto to be used for mapping to an entity
	// This is the case in PATCH request

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
