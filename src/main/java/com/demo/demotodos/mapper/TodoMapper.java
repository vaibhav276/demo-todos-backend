package com.demo.demotodos.mapper;

import com.demo.demotodos.dto.TodoDto;
import com.demo.demotodos.model.Todo;

public class TodoMapper {
	public static Todo mapToTodo(TodoDto todoDto, Todo todo) {
		todo.setDue(todoDto.getDue());
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setDone(todoDto.isDone());
		return todo;
	}

	public static TodoDto mapToTodoDto(Todo todo, TodoDto todoDto) {
		todoDto.setDue(todo.getDue());
		todoDto.setTitle(todo.getTitle());
		todoDto.setDescription(todo.getDescription());
		todoDto.setDone(todo.isDone());
		return todoDto;
	}
}
