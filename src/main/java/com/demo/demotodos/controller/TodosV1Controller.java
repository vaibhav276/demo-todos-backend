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
import com.demo.demotodos.service.ITodosService;
import com.demo.demotodos.exception.ResourceNotFoundException;

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
	ITodosService todosService;

	/**
	 * Get all Todos of a given User
	 * @param userId
	 * @param done
	 * @return List<TodoDto> - list of created Todos
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<TodoDto>> getTodos(
		@RequestHeader("user_id") String userId,
		@RequestParam("done") Optional<Boolean> done
	) {

		List<Todo> todos = todosService.getTodos(userId, done);

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

	/**
	 * Add a given new Todo to a given User
	 * @param userId
	 * @param Todo in json format
	 * @return TodoDto - Created Todo
	 */
	@PostMapping("/todos")
	public ResponseEntity<TodoDto> postTodo(
		@RequestHeader("user_id") String userId,
		@RequestBody TodoDto todoDto
		) {
		Todo todo = new Todo();
		todo = TodoMapper.mapToTodo(todoDto, todo);

		todo = todosService.createTodo(userId, todo);
		
		todoDto.setTodoId(todo.getTodoId());

		return ResponseEntity
				.ok()
				.body(todoDto);
	}

	/**
	 * Get all fields of given Todo
	 * @param userId
	 * @param todoId
	 * @return TodoDto - Found Todo
	 */
	@GetMapping("/todo/t/{todo_id}")
	public ResponseEntity<TodoDto> getTodo(
		@RequestHeader("user_id") String userId,
		@PathVariable("todo_id") String todoId
	) {
		Todo todo = todosService.getTodo(userId, todoId);
		TodoDto todoDto = TodoMapper.mapToTodoDto(todo, new TodoDto());
		return ResponseEntity.ok().body(todoDto);
	}
	
	/**
	 * Update fields of a given Todo
	 * @param userId
	 * @param todoId
	 * @param todoDto
	 * @return TodoDto - Updated Todo
	 */
	@PatchMapping("/todo/t/{todo_id}")
	public ResponseEntity<TodoDto> patchTodo(
		@RequestHeader("user_id") String userId,
		@PathVariable("todo_id") String todoId,
		@RequestBody TodoDto todoDto
	) {
		Todo todo = todosService.getTodo(userId, todoId);
		todo = TodoMapper.mapNonNullToTodo(todoDto, todo);
		todosService.patchTodo(userId, todoId, todo);
		return ResponseEntity
			.ok()
			.body(TodoMapper.mapToTodoDto(todo, new TodoDto()));
	}

	/**
	 * Delete a given Todo
	 * @param userId
	 * @param todoId
	 * @return null - Nothing if successful
	 */
	@DeleteMapping("/todo/t/{todo_id}")
	public ResponseEntity<Object> deleteTodo(
		@RequestHeader("user_id") String userId,
		@PathVariable("todo_id") String todoId
	) {
		boolean success = todosService.deleteTodo(userId, todoId);
		if (success) {
			return ResponseEntity.ok().body(null);
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}
}
