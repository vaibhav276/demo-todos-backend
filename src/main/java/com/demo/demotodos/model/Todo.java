package com.demo.demotodos.model;

import java.sql.Timestamp;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
public class Todo {
	@PrimaryKey
	private Timestamp due;

	private String title;
	private String description;
	private boolean done;
}
