package com.example.todosummary.controller;

import com.example.todosummary.model.Todo;
import com.example.todosummary.model.User;
import com.example.todosummary.repository.TodoRepository;
import com.example.todosummary.repository.UserRepository;
import com.example.todosummary.service.LlmService;
import com.example.todosummary.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SummaryController {
    @Autowired private TodoRepository todoRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private LlmService llmService;
    @Autowired private SlackService slackService;

    @PostMapping("/summarize")
    public ResponseEntity<?> summarize(Principal principal) {
        User user = userRepo.findByUsername(principal.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        List<Todo> todos = todoRepo.findByUser(user).stream()
            .filter(t -> !t.getCompleted()).collect(Collectors.toList());
        List<String> todoTexts = todos.stream().map(Todo::getTitle).collect(Collectors.toList());
        if (todoTexts.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "No pending todos to summarize."));
        }
        String summary = llmService.summarizeTodos(todoTexts);
        boolean slackSuccess = slackService.sendSummary("Pending todos summary for " + user.getUsername() + ":\n" + summary);
        if (slackSuccess) {
            return ResponseEntity.ok(Map.of("message", "Summary sent to Slack"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Summary generated, but failed to send to Slack."));
        }
    }
}