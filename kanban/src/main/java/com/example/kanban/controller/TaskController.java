package com.example.kanban.controller;

import com.example.kanban.model.Task;
import com.example.kanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task)); 
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Task>>> getAllTasksByStatus() {
        Map<String, List<Task>> tasksByStatus = Map.of(
                "A_FAZER", taskService.getTasksByStatus(Task.StatusTarefa.A_FAZER),
                "EM_PROGRESSO", taskService.getTasksByStatus(Task.StatusTarefa.EM_PROGRESSO),
                "CONCLUIDO", taskService.getTasksByStatus(Task.StatusTarefa.CONCLUIDO)
        );
        return ResponseEntity.ok(tasksByStatus);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long id,
            @RequestBody Task.StatusTarefa newStatus) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, newStatus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/prioridade/{prioridade}")
    public ResponseEntity<List<Task>> getTasksByPrioridade(
            @PathVariable Task.Prioridade prioridade) {
        return ResponseEntity.ok(taskService.getTasksByPrioridade(prioridade));
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<Task>> getTasksAtrasadas() {
        return ResponseEntity.ok(taskService.getAtrasadas());
    }
}
