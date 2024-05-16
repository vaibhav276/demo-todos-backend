package com.demo.demotodos.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
    name = "Todo",
    description = "Schema to hold a Todo item"
)
@Data
public class TodoDto {
	@Schema(
		name = "todo_id",
		description = "ID of the Todo",
		example = "01HY0BY09JHN0CDNFGX20PJCZN"
	)
	@JsonProperty("todo_id")
	@Null(message = "Cannot provide a todo_id in input body")
	private String todoId;

	@Schema(
		name = "due_date",
		description = "Due date of the Todo",
		example = "2024-09-12T00:00:00.000+00:00"
	)
	@FutureOrPresent(message = "due_date cannot be in the past")
	@NotNull(message = "due_date cannot be null")
	@JsonProperty(value = "due_date")
	private Date dueDate;

	@Schema(
		name = "title",
		description = "Title of the Todo",
		example = "A short title"
	)
	@NotBlank(message = "title cannot be empty or null")
	@Size(max = 128, message = "title cannot be more than 128 characters long")
	private String title;

	@Schema(
		name = "description",
		description = "Description of the Todo",
		example = "Some optional description that helps capture what exactly needs to be done"
	)
	@Size(max = 1024, message = "description cannot be more than 1Kb")
	private String description;

	@Schema(
		name = "done",
		description = "Indicates whether the Todo is done",
		example = "true"
	)
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
