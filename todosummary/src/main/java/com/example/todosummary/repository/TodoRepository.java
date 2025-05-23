package com.example.todosummary.repository;

import com.example.todosummary.model.Todo;
import com.example.todosummary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);
}