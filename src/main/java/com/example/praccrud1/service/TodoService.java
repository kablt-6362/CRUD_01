package com.example.praccrud1.service;

import com.example.praccrud1.repository.TodoRepository;
import com.example.praccrud1.tododto.TodoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDto> getAll(){
        return todoRepository.getAll();
    }

    public void save(TodoDto todo){
        todoRepository.save(todo);
    }

    public TodoDto getById(Long id){
        return todoRepository.getById(id)
                .orElseThrow(()->new IllegalArgumentException("Not-found"));
    }

    public void delete(Long id){
        todoRepository.delete(id);
    }

    public List<TodoDto> findByTitleContaining(String keyword){
        return todoRepository.findByTitleContaining(keyword);
    }

    public List<TodoDto> findByCompleted(boolean completed){
        return todoRepository.findByCompleted(completed);
    }


}
