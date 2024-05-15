package com.demo.demotodos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demotodos.dto.TodoDto;
import com.demo.demotodos.mapper.TodoMapper;
import com.demo.demotodos.model.Todo;
import com.demo.demotodos.repository.TodosRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path = "/api/v1")
public class TodosV1Controller {

	@Autowired
	TodosRepository todosRepository;

	@GetMapping("/todos")
	public ResponseEntity<List<TodoDto>> getTodos() {
		try {
			Iterable<Todo> todos = todosRepository.findAll();
			List<TodoDto> todosDto = new ArrayList<>();
			for (Todo t : todos) {
				TodoDto td = new TodoDto();
				td = TodoMapper.mapToTodoDto(t, td);
				todosDto.add(td);
			}

			return ResponseEntity
					.ok()
					.body(todosDto);
		} catch (Exception e) {
			return ResponseEntity
					.internalServerError()
					.body(null); // TODO: Global exception handler
		}
	}

	@PostMapping("/todos")
	public ResponseEntity<TodoDto> postTodo(@RequestBody TodoDto todoDto) {
		try {
			Todo todo = new Todo();
			todo = TodoMapper.mapToTodo(todoDto, todo);
			todosRepository.save(todo);

			return ResponseEntity
					.ok()
					.body(todoDto);
		} catch (Exception e) {
			return ResponseEntity
				.internalServerError()
				.body(null); // TODO: Global exception handler
		}
	}
}
