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

    public void createTodo(TodoDto todo){
        titleCheck(todo.getTitle());
        todoRepository.save(todo);
    }

    public TodoDto updateTodoById(long id,TodoDto newTodo){
        titleCheck(newTodo.getTitle());
        TodoDto originTodo = getById(id);

        originTodo.setTitle(newTodo.getTitle());
        originTodo.setContent(newTodo.getContent());
        originTodo.setCompleted(newTodo.getCompleted());

        return todoRepository.save(originTodo);
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

    private void titleCheck(String title){
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("제목이 비어있습니다");
        }
        if(title.length() > 50){
            throw new IllegalArgumentException(" 50글자를 넘길 수 없습니다.");
        }
    }

    public long totalCount(){
        long total =todoRepository.getAll().size();
        return total;
    }

    public long active(){
        long active = todoRepository.findByCompleted(false).size();
        return active;
    }

    public long completed(){
        long completed = todoRepository.findByCompleted(true).size();
        return completed;
    }

    public void deleteCompleted(){
        todoRepository.deleteCompleted();
    }

}
