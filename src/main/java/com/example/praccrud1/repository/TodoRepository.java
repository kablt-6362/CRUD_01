package com.example.praccrud1.repository;

import com.example.praccrud1.tododto.TodoDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TodoRepository {
    private final Map<Long, TodoDto> storage = new HashMap<>();
    private Long id  =1L;

    public TodoDto save(TodoDto todo){
        if(todo.getId()==null){
        todo.setId(id++);
        }
        storage.put(todo.getId(),todo);
        return todo;
    }

    public List<TodoDto> getAll(){
        return new ArrayList<>(storage.values());
    }

    public Optional<TodoDto> getById(Long id){
        return Optional.ofNullable(storage.get(id));
    }

    public void delete(Long id){
        storage.remove(id);
    }

    public List<TodoDto> findByTitleContaining(String keyword){
        return storage.values().stream()
                .filter((todo)->todo.getTitle().contains(keyword)).toList();
    }

    public List<TodoDto> findByCompleted(Boolean completed){
        return storage.values().stream()
                .filter((todo)->todo.getCompleted()==completed).toList();
    }

    public void deleteCompleted(){
        storage.entrySet().removeIf(
                item->item.getValue().getCompleted()
        );
    }
}
