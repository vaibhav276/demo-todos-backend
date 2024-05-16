package com.demo.demotodos.service.impl;

import java.util.List;
import java.util.Optional;
import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demotodos.exception.ResourceNotFoundException;
import com.demo.demotodos.model.Todo;
import com.demo.demotodos.repository.TodosRepository;
import com.demo.demotodos.service.ITodosService;

@Service
public class TodosService implements ITodosService {

	@Autowired
	TodosRepository todosRepository;

	@Override
	public List<Todo> getTodos(String userId, Optional<Boolean> done) {
		return done.isEmpty() ? 
			todosRepository.findByUserId(userId) 
			: todosRepository.findByUserIdAndDone(userId, done.get());
	}

	@Override
	public Todo createTodo(String userId, Todo todo) {
		Ulid ulid = UlidCreator.getUlid();
		todo.setTodoId(ulid.toString());
		todo.setUserId(userId);
		return todosRepository.save(todo);
	}

	@Override
	public Todo getTodo(String userId, String todoId) {
		return todosRepository.findByUserIdAndTodoId(userId, todoId).orElseThrow(
			() -> new ResourceNotFoundException("Todo", String.format("user_id = %s, todo_id = %s", userId, todoId))
		);
	}

	@Override
	public Todo patchTodo(String userId, String todoId, Todo todo) {
		todosRepository.findByUserIdAndTodoId(userId, todoId).orElseThrow(
			() -> new ResourceNotFoundException("Todo", String.format("user_id = %s, todo_id = %s", userId, todoId))
		);
		return todosRepository.save(todo);
	}

	@Override
	public boolean deleteTodo(String userId, String todoId) {
		Todo todo = todosRepository.findByUserIdAndTodoId(userId, todoId).orElseThrow(
			() -> new ResourceNotFoundException("Todo", String.format("user_id = %s, todo_id = %s", userId, todoId))
		);
		todosRepository.delete(todo);
		return true;
	}
	
}
