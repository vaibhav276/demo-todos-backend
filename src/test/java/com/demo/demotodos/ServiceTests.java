package com.demo.demotodos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.demotodos.model.Todo;
import com.demo.demotodos.repository.TodosRepository;
import com.demo.demotodos.service.impl.TodosService;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {
	
	@InjectMocks
	TodosService todosService;

	@Mock
	TodosRepository todosRepository;

	@Test
	void getsTodos() {
		when(todosRepository.findByUserId("test_user")).thenReturn(List.of(
			new Todo("test_user", "TODO123", new Date(), "Test title 1", "Test description 1", false),
			new Todo("test_user", "TODO124", new Date(), "Test title 2", "Test description 2", false)
		));

		List<Todo> todos = todosService.getTodos("test_user", Optional.empty());
		assertEquals(2, todos.size());

		verify(todosRepository, times(1)).findByUserId("test_user");
	}
}
