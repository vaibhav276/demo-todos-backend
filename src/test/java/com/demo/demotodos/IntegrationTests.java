package com.demo.demotodos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.demo.demotodos.controller.TodosV1Controller;
import com.demo.demotodos.dto.TodoDto;
import com.demo.demotodos.dto.TodoListDto;

/**
 * TODO: Fix tests with security enabled
 */

@SpringBootTest
public class IntegrationTests {
	@Autowired
	TodosV1Controller todosController;

	@Test
	void createReadDelete() {
		TodoDto todoDto = new TodoDto();
		todoDto.setTitle("test todo");
		todoDto.setDescription("test description");
		todoDto.setDueDate(new Date(124, 9, 12));
		todoDto.setDone(false);

		final String userId = "test_user";

		ResponseEntity<TodoDto> createResponse = todosController.postTodo(null, todoDto);

		Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		ResponseEntity<TodoListDto> getResponse = todosController.getTodos(null, Optional.empty());

		Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(getResponse.getBody()).hasFieldOrProperty("count");
		Assertions.assertThat(getResponse.getBody()).hasFieldOrProperty("todos");
		Assertions.assertThat(getResponse.getBody().getTodos()).hasSizeGreaterThanOrEqualTo(1);

		List<TodoDto> todos = getResponse.getBody().getTodos();

		for (TodoDto td : todos) {
			ResponseEntity<Object> deleteResponse = todosController.deleteTodo(null, td.getTodoId());
			Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		}
	}
}
