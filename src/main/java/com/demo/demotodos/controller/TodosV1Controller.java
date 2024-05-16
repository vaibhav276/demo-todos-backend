package com.demo.demotodos.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import com.demo.demotodos.dto.TodoDto;
import com.demo.demotodos.mapper.TodoMapper;
import com.demo.demotodos.model.Todo;
import com.demo.demotodos.service.ITodosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "REST APIs for Demo Todos", description = "REST APIs for Demo Todos")
@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path = "/api/v1")
@Validated
public class TodosV1Controller {

	@Autowired
	ITodosService todosService;

	/**
	 * Get all Todos of a given User
	 * 
	 * @param userId
	 * @param done
	 * @return List<TodoDto> - list of created Todos
	 */
	@Operation(summary = "Get Todos")
	@ApiResponses({
			@ApiResponse(
				responseCode = "200",
				description = "200 OK",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = TodoDto.class)
				)
			)
	})
	@GetMapping("/todos")
	public ResponseEntity<List<TodoDto>> getTodos(
			@NotBlank(message = "user_id cannot be empty or null") @RequestHeader("user_id") String userId,

			@RequestParam("done") Optional<Boolean> done) {

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
	 * 
	 * @param userId
	 * @param Todo   in json format
	 * @return TodoDto - Created Todo
	 */
	@Operation(summary = "Create a Todo")
	@ApiResponses({
			@ApiResponse(
				responseCode = "201",
				description = "201 CREATED",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = TodoDto.class)
				)
			)
	})
	@PostMapping("/todos")
	public ResponseEntity<TodoDto> postTodo(
			@NotBlank(message = "user_id cannot be empty or null") @RequestHeader("user_id") String userId,

			@Valid @RequestBody TodoDto todoDto) {
		Todo todo = new Todo();
		todo = TodoMapper.mapToTodo(todoDto, todo);

		todo = todosService.createTodo(userId, todo);

		todoDto.setTodoId(todo.getTodoId());

		return ResponseEntity
				.created(URI.create(String.format("/todo/t/%s", todoDto.getTodoId())))
				.body(todoDto);
	}

	/**
	 * Get all fields of given Todo
	 * 
	 * @param userId
	 * @param todoId
	 * @return TodoDto - Found Todo
	 */
	@Operation(summary = "Get a Todo")
	@ApiResponses({
			@ApiResponse(
				responseCode = "200",
				description = "200 OK",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = TodoDto.class)
				)
			)
	})
	@GetMapping("/todo/t/{todo_id}")
	public ResponseEntity<TodoDto> getTodo(
			@NotBlank(message = "user_id cannot be empty or null") @RequestHeader("user_id") String userId,

			@NotBlank(message = "todo_id cannot be empty or null") @PathVariable("todo_id") String todoId) {
		Todo todo = todosService.getTodo(userId, todoId);
		TodoDto todoDto = TodoMapper.mapToTodoDto(todo, new TodoDto());
		return ResponseEntity.ok().body(todoDto);
	}

	/**
	 * Update fields of a given Todo. Only fields to be updated need to be sent in
	 * request
	 * 
	 * TODO: No validation on input JSON because its acceptable to send nulls or
	 * empty for fields that don't need updating
	 * 
	 * @param userId
	 * @param todoId
	 * @param todoDto
	 * @return TodoDto - Updated Todo
	 */
	@Operation(summary = "Update a Todo")
	@PatchMapping("/todo/t/{todo_id}")
	@ApiResponses({
			@ApiResponse(
				responseCode = "200",
				description = "200 OK",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = TodoDto.class)
				)
			)
	})
	public ResponseEntity<TodoDto> patchTodo(
			@NotBlank(message = "user_id cannot be empty or null") @RequestHeader("user_id") String userId,

			@NotBlank(message = "todo_id cannot be empty or null") @PathVariable("todo_id") String todoId,

			@RequestBody TodoDto todoDto) {
		Todo todo = todosService.getTodo(userId, todoId);
		todo = TodoMapper.mapNonNullToTodo(todoDto, todo);
		todosService.patchTodo(userId, todoId, todo);
		return ResponseEntity
				.ok()
				.body(TodoMapper.mapToTodoDto(todo, new TodoDto()));
	}

	/**
	 * Delete a given Todo
	 * 
	 * @param userId
	 * @param todoId
	 * @return null - Nothing if successful
	 */
	@Operation(summary = "Delete a Todo")
	@ApiResponses({
			@ApiResponse(
				responseCode = "200",
				description = "200 OK"
			)
	})
	@DeleteMapping("/todo/t/{todo_id}")
	public ResponseEntity<Object> deleteTodo(
			@NotBlank(message = "user_id cannot be empty or null") @RequestHeader("user_id") String userId,

			@NotBlank(message = "todo_id cannot be empty or null") @PathVariable("todo_id") String todoId) {
		boolean success = todosService.deleteTodo(userId, todoId);
		if (success) {
			return ResponseEntity.ok().body(null);
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}
}
