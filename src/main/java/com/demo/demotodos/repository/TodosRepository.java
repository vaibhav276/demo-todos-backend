package com.demo.demotodos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.demo.demotodos.model.Todo;

public interface TodosRepository extends MapIdCassandraRepository<Todo> {
	List<Todo> findByUserId(String userId);

	@AllowFiltering
	List<Todo> findByUserIdAndDone(String userId, Boolean done);

	Optional<Todo> findByUserIdAndTodoId(String userId, String todoId);
}
