package com.demo.demotodos.mapper;

import com.demo.demotodos.dto.TodoDto;
import com.demo.demotodos.model.Todo;

public class TodoMapper {
	public static Todo mapToTodo(TodoDto todoDto, Todo todo) {
		todo.setDueDate(todoDto.getDueDate());
		todo.setTitle(todoDto.getTitle());
		todo.setDescription(todoDto.getDescription());
		todo.setDone(todoDto.getDone());
		return todo;
	}

	public static Todo mapNonNullToTodo(TodoDto todoDto, Todo todo) {
		if (todoDto.hasDueDate())     todo.setDueDate(todoDto.getDueDate());
		if (todoDto.hasTitle())       todo.setTitle(todoDto.getTitle());
		if (todoDto.hasDescription()) todo.setDescription(todoDto.getDescription());
		if (todoDto.hasDone())        todo.setDone(todoDto.getDone());
		return todo;
	}

	public static TodoDto mapToTodoDto(Todo todo, TodoDto todoDto) {
		todoDto.setTodoId(todo.getTodoId());
		todoDto.setDueDate(todo.getDueDate());
		todoDto.setTitle(todo.getTitle());
		todoDto.setDescription(todo.getDescription());
		todoDto.setDone(todo.getDone());
		return todoDto;
	}
}
