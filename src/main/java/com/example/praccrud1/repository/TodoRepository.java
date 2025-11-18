package com.example.praccrud1.repository;

import com.example.praccrud1.tododto.TodoDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepository {
    private final Map<Long, TodoDto> storage = new HashMap<>();
    private Long id  =1L;

    public TodoDto save(TodoDto todo){
        todo.setId(id++);
        storage.put(todo.getId(),todo);
        return todo;
    }

    public List<TodoDto> getAll(){
        return new ArrayList<>(storage.values());
    }

    public TodoDto getById(Long id){
        return storage.get(id);
    }

}
