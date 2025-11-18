package com.example.praccrud1.controller;


import com.example.praccrud1.repository.TodoRepository;
import com.example.praccrud1.tododto.TodoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoController {

    private final TodoRepository repository;
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public String todos(Model model){
        List<TodoDto> list = repository.getAll();
        model.addAttribute("list",list);
        return "todos";
    }

    @GetMapping("/todos/new")
    public String newTodo(){
        return "new";
    }
    @GetMapping("/todos/create")
    public String create(@RequestParam String title, @RequestParam String content, Model model){
        TodoDto tododto = new TodoDto(null,title,content,false);
        //TodoRepository repository = new TodoRepository();
        TodoDto todo = repository.save(tododto);
        model.addAttribute("todo",todo);
        return "redirect:/todos";
    }

    @GetMapping("/todos/detail/{id}")
    public String detail(@PathVariable Long id,Model model){
        TodoDto todo = repository.getById(id);
        model.addAttribute("todo",todo);
        return "detail";
    }

    @GetMapping("todos/delete/{id}")
    public String delete(@PathVariable Long id,
                         Model model){
        repository.delete(id);
        return "redirect:/todos";
    }
}
