package com.example.todosummary.controller;

import com.example.todosummary.model.Todo;
import com.example.todosummary.model.User;
import com.example.todosummary.repository.TodoRepository;
import com.example.todosummary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired private TodoRepository todoRepo;
    @Autowired private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(Principal principal) {
        User user = userRepo.findByUsername(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        return ResponseEntity.ok(todoRepo.findByUser(user));
    }

    @PostMapping
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, Principal principal) {
        User user = userRepo.findByUsername(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        todo.setUser(user);
        if (todo.getCompleted() == null) todo.setCompleted(false);
        return ResponseEntity.ok(todoRepo.save(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTodo(@PathVariable Long id, @RequestBody Todo reqTodo, Principal principal) {
        Todo todo = todoRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        if (!todo.getUser().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        todo.setTitle(reqTodo.getTitle());
        todo.setCompleted(reqTodo.getCompleted());
        return ResponseEntity.ok(todoRepo.save(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, Principal principal) {
        Todo todo = todoRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        if (!todo.getUser().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        todoRepo.delete(todo);
        return ResponseEntity.ok().build();
    }
}