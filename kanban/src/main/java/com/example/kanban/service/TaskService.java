package com.example.kanban.service;

import com.example.kanban.model.Task;
import com.example.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        if (task.getTitulo() == null || task.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título é obrigatório");
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(Task.StatusTarefa status) {
        return taskRepository.findByStatusOrderByPrioridadeDesc(status);
    }

    public Task updateTaskStatus(Long id, Task.StatusTarefa newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (updatedTask.getTitulo() != null) {
            task.setTitulo(updatedTask.getTitulo());
        }
        if (updatedTask.getDescricao() != null) {
            task.setDescricao(updatedTask.getDescricao());
        }
        if (updatedTask.getPrioridade() != null) {
            task.setPrioridade(updatedTask.getPrioridade());
        }
        if (updatedTask.getDataLimite() != null) {
            task.setDataLimite(updatedTask.getDataLimite());
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByPrioridade(Task.Prioridade prioridade) {
        return taskRepository.findByPrioridade(prioridade);
    }

    public List<Task> getAtrasadas() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .filter(task -> task.getDataLimite() != null &&
                        task.getDataLimite().isBefore(LocalDateTime.now()) &&
                        task.getStatus() != Task.StatusTarefa.CONCLUIDO)
                .toList();
    }
}