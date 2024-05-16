package com.demo.demotodos.model;

import java.util.Date;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
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
@Table(value = "user_todo")
public class Todo {

	@PrimaryKeyColumn(
		name = "user_id",
		ordinal = 0,
		type = PrimaryKeyType.PARTITIONED
	)
	private String userId;

	@PrimaryKeyColumn(
		name = "todo_id",
		ordinal = 1,
		type = PrimaryKeyType.CLUSTERED,
		ordering = Ordering.ASCENDING
	)
	private String todoId;

	private Date dueDate;
	private String title;
	private String description;
	private Boolean done;
}
