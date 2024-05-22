package com.demo.demotodos;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.demotodos.model.Todo;
import com.demo.demotodos.repository.TodosRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TodoTestDataLoader implements CommandLineRunner {
	static final Logger log = LoggerFactory.getLogger(TodoTestDataLoader.class);

	TodosRepository todosRepository;
	ObjectMapper objectMapper;

	public TodoTestDataLoader(TodosRepository todosRepository, ObjectMapper objectMapper) {
		this.todosRepository = todosRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		if (todosRepository.count() == 0) {
			final String TODOS_JSON = "/data/todos.json";
			log.info("Loading todos into database from file: {}", TODOS_JSON);
			try (InputStream inputStream = TypeReference.class.getResourceAsStream(TODOS_JSON)) {
				Todos todoList = objectMapper.readValue(inputStream, Todos.class);
				for (Todo todo : todoList.todos()) {
					log.info("Inserting value: user_id = {}, todo_id = {}, title = {}", todo.getUserId(), todo.getTodoId(), todo.getTitle());
				}
				todosRepository.saveAll(todoList.todos());
			} catch(IOException e) {
				throw new RuntimeException("Failed to read json data from file: " + TODOS_JSON);
			}
		}
	}
}

/**
 * Todos
 */
record Todos(List<Todo> todos) {
}