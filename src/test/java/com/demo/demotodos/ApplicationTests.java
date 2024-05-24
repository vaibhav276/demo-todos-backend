package com.demo.demotodos;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.demotodos.controller.TodosV1Controller;

@SpringBootTest
class ApplicationTests {

	@Autowired
	TodosV1Controller todosController;

	@Test
	void contextLoads() {
		Assertions.assertThat(todosController).isNotNull();
	}

}
