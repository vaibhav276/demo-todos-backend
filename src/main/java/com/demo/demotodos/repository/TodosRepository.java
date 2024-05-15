package com.demo.demotodos.repository;

import java.sql.Timestamp;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.demo.demotodos.model.Todo;

public interface TodosRepository extends CassandraRepository<Todo, Timestamp> {
}
