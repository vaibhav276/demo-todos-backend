package com.demo.demotodos.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
	name = "List of Todos",
  description = "Schema to hold a list of Todo items"
)
@Data
public class TodoListDto {
	private Integer count;
	private List<TodoDto> todos;

	public TodoListDto() {
		count = 0;
		todos = new ArrayList<>();
	}

	public void add(TodoDto todoDto) {
		todos.add(todoDto);
		count++;
	}
}
