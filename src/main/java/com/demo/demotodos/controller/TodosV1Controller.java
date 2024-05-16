package com.demo.demotodos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import com.demo.demotodos.dto.TodoDto;
import com.demo.demotodos.mapper.TodoMapper;
import com.demo.demotodos.model.Todo;
import com.demo.demotodos.repository.TodosRepository;
import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path = "/api/v1")
public class TodosV1Controller {

	@Autowired
	TodosRepository todosRepository;

	// Get all todos of a given user_id
	@GetMapping("/todos")
	public ResponseEntity<List<TodoDto>> getTodos(
		@RequestHeader("user_id") String userId,
		@RequestParam("done") Optional<Boolean> done
	) {
		Iterable<Todo> todos = done.isEmpty() ? 
			todosRepository.findByUserId(userId) 
			: todosRepository.findByUserIdAndDone(userId, done.get());

		List<TodoDto> todosDto = new ArrayList<>();
		for (Todo t : todos) {
			TodoDto td = new TodoDto();
			td = TodoMapper.mapToTodoDto(t, td);
			todosDto.add(td);
		}

		return ResponseEntity
				.ok()
				.body(todosDto);
	}

	// Add a given new todo to a given user_id
	@PostMapping("/todos")
	public ResponseEntity<TodoDto> postTodo(
		@RequestHeader("user_id") String userId,
		@RequestBody TodoDto todoDto
		) {
		Todo todo = new Todo();
		todo = TodoMapper.mapToTodo(todoDto, todo);
		Ulid ulid = UlidCreator.getUlid();
		todo.setTodoId(ulid.toString());
		todo.setUserId(userId);
		todosRepository.save(todo);

		todoDto.setTodoId(todo.getTodoId());

		return ResponseEntity
				.ok()
				.body(todoDto);
	}

	// Get all fields of given todo_id
	@GetMapping("/todo/t/{todo_id}")
	public ResponseEntity<TodoDto> getTodo(
		@RequestHeader("user_id") String userId,
		@PathVariable("todo_id") String todoId
	) {
		Optional<Todo> oTodo = todosRepository.findByUserIdAndTodoId(userId, todoId);
		if (oTodo.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			TodoDto todoDto = new TodoDto();
			TodoMapper.mapToTodoDto(oTodo.get(), todoDto);
			return ResponseEntity.ok().body(todoDto);
		}
	}
	
	// Update fields of a given todo_id
	@PatchMapping("/todo/t/{todo_id}")
	public ResponseEntity<TodoDto> patchTodo(
		@RequestHeader("user_id") String userId,
		@PathVariable("todo_id") String todoId,
		@RequestBody TodoDto todoDto
	) {
		Optional<Todo> oTodo = todosRepository.findByUserIdAndTodoId(userId, todoId);
		if (oTodo.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			Todo todo = oTodo.get();
			TodoMapper.mapNonNullToTodo(todoDto, todo);
			todosRepository.save(todo);

			Optional<Todo> oTodo1 = todosRepository.findByUserIdAndTodoId(userId, todoId);
			if (oTodo1.isPresent()) {
				TodoDto todoDto1 = new TodoDto();
				TodoMapper.mapToTodoDto(oTodo1.get(), todoDto1);
				return ResponseEntity.ok().body(todoDto1);
			} else {
				return ResponseEntity.internalServerError().build();
			}
		}
	}

	// Delete a given todo_id
	@DeleteMapping("/todo/t/{todo_id}")
	public ResponseEntity<Object> deleteTodo(
		@RequestHeader("user_id") String userId,
		@PathVariable("todo_id") String todoId
	) {
		Optional<Todo> oTodo = todosRepository.findByUserIdAndTodoId(userId, todoId);
		if (oTodo.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			todosRepository.delete(oTodo.get());
			return ResponseEntity.ok(null);
		}
	}
}
