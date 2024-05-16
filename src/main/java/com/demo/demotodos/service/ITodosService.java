package com.demo.demotodos.service;

import java.util.List;
import java.util.Optional;

import com.demo.demotodos.model.Todo;

public interface ITodosService {
	public List<Todo> getTodos(String userId, Optional<Boolean> done);

	public Todo createTodo(String userId, Todo todo);

	public Todo getTodo(String userId, String todoId);

	public Todo patchTodo(String userId, String todoId, Todo todo);

	public boolean deleteTodo(String userId, String todoId);
}
