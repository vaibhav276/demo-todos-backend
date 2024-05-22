package com.demo.demotodos;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.demotodos.controller.TodosV1Controller;
import com.demo.demotodos.service.ITodosService;
import com.demo.demotodos.model.Todo;

@WebMvcTest(TodosV1Controller.class)
@AutoConfigureMockMvc
public class DemoTodosControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ITodosService todosService;

	@BeforeEach
	void setup() {

	}

	@Test
	void getsAllTodos() throws Exception {

		when(todosService.getTodos("test_user", Optional.empty())).thenReturn(
			List.of(
				new Todo("test_user", "TODO123", new Date(), "Test title 1", "Test description 1", false),
				new Todo("test_user", "TODO124", new Date(), "Test title 2", "Test description 2", false)
			)
		);

		String jsonResponse = """
				{
					"count": 2,
					"todos": [
						{
							"todo_id": "TODO123",
							"title": "Test title 1",
							"description": "Test description 1",
							"done": false
						},
						{
							"todo_id": "TODO124",
							"title": "Test title 2",
							"description": "Test description 2",
							"done": false
						}
					]
				}
				""";

		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/v1/todos")
				.header("user_id", "test_user")
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}

	@Test
	void getsAllTodosWithDoneTrue() throws Exception {

		when(todosService.getTodos("test_user", Optional.of(true))).thenReturn(
			List.of(
				new Todo("test_user", "TODO123", new Date(), "Test title 1", "Test description 1", true),
				new Todo("test_user", "TODO124", new Date(), "Test title 2", "Test description 2", true)
			)
		);

		String jsonResponse = """
				{
					"count": 2,
					"todos": [
						{
							"todo_id": "TODO123",
							"title": "Test title 1",
							"description": "Test description 1",
							"done": true
						},
						{
							"todo_id": "TODO124",
							"title": "Test title 2",
							"description": "Test description 2",
							"done": true
						}
					]
				}
				""";

		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/v1/todos")
				.header("user_id", "test_user")
				.param("done", "true")
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}

	@Test
	void getsAllTodosWithDoneFalse() throws Exception {

		when(todosService.getTodos("test_user", Optional.of(false))).thenReturn(
			List.of(
				new Todo("test_user", "TODO123", new Date(), "Test title 1", "Test description 1", false),
				new Todo("test_user", "TODO124", new Date(), "Test title 2", "Test description 2", false)
			)
		);

		String jsonResponse = """
				{
					"count": 2,
					"todos": [
						{
							"todo_id": "TODO123",
							"title": "Test title 1",
							"description": "Test description 1",
							"done": false
						},
						{
							"todo_id": "TODO124",
							"title": "Test title 2",
							"description": "Test description 2",
							"done": false
						}
					]
				}
				""";

		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/v1/todos")
				.header("user_id", "test_user")
				.param("done", "false")
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	}

	@Test
	void failGetAllWithBlankUserId() throws Exception {

		// Internal server error because GlobalExceptionHandler isn't loaded in
		// this limited context
		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/v1/todos")
				.header("user_id", "")
			)
			.andExpect(MockMvcResultMatchers.status().isInternalServerError());

		// ...and the service method shouldn't have been called
		verify(todosService, times(0)).getTodos("", Optional.empty());
	}
}
