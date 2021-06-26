package com.topcoder.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.topcoder.demo.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
	
}
