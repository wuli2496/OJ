package com.topcoder.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topcoder.demo.dto.GenericResponse;
import com.topcoder.demo.model.Todo;
import com.topcoder.demo.repository.TodoRepository;
import com.topcoder.demo.util.JpaUtil;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@GetMapping()
	public GenericResponse<List<Todo>> listTodos(@RequestBody Todo todo) {
		Example<Todo> example = Example.of(todo);
		List<Todo> todos = todoRepository.findAll(example);
		return GenericResponse.success(todos);
	}
	
	@GetMapping("{id}")
	public GenericResponse<Todo> getTodo(@PathVariable("id") Long id) {
		Optional<Todo> todo = todoRepository.findById(id);
		if (!todo.isPresent()) {
			return GenericResponse.success(null);
		}
		
		return GenericResponse.success(todo.get());
	}
	
	@PostMapping() 
	public GenericResponse<Todo> createTodo(@RequestBody @Valid Todo todo) {
		Todo todoNew = todoRepository.save(todo);
		
		return GenericResponse.success(todoNew);
	}
	
	@PutMapping("{id}")
	public GenericResponse<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
		Optional<Todo> todoExist = todoRepository.findById(id);
		if (!todoExist.isPresent()) {
			return GenericResponse.failure(-1, "不存在");
		}
		
		Todo todoNew = todoExist.get();
		JpaUtil.copyNotNullProperties(todo, todoNew);
		
		return GenericResponse.success(todoRepository.save(todoNew));
	}
	
	@DeleteMapping("{id}")
	public  GenericResponse<Boolean> deleteTodo(@PathVariable("id") Long id) {
		todoRepository.deleteById(id);
		
		return GenericResponse.success(true);
	}
}
