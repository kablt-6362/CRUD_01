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
        List<TodoDto> todos = repository.getAll();
        model.addAttribute("todos",todos);
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
//        TodoDto todo = repository.getById(id);
        try{
            TodoDto todo = repository.getById(id).orElseThrow(()-> new IllegalArgumentException("todo not found"));
            model.addAttribute("todo",todo);
            return "detail";
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("todos/delete/{id}")
    public String delete(@PathVariable Long id,
                         Model model){
        repository.delete(id);
        return "redirect:/todos";
    }

    @GetMapping("/todos/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        //TodoDto todo = repository.getById(id);
        try{
         TodoDto todo = repository.getById(id).orElseThrow(()->new IllegalArgumentException("todo not found"));
        model.addAttribute("todo",todo);
        return "edit";
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/todos/update/{id}")
    public String update(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam String content,
                       @RequestParam(defaultValue = "false") Boolean completed,
                       Model model){
        // tododto객체를 새로생성해서 생성자 매개변수에 값을 넣어서 만들면 안될가
        //TodoDto todo = repository.getById(id);
        try{
            TodoDto todo = repository.getById(id).orElseThrow(()->new IllegalArgumentException("todo not found"));
            todo.setTitle(title);
            todo.setContent(content);
            todo.setCompleted(completed);
            repository.save(todo);
            return "redirect:/todos/detail/"+id;
        }
        catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/todos/search")
    public String search(@RequestParam String keyword,Model model){
        List<TodoDto> todos = repository.findByTitleContaining(keyword);
        model.addAttribute("todos",todos);
        return "todos";
    }

    @GetMapping("/todos/active")
    public String active(Model model){
        List<TodoDto> todos = repository.findByCompleted(false);
        model.addAttribute("todos",todos);
        return "/todos";
    }

    @GetMapping("/todos/completed")
    public String completed(Model model){
        List<TodoDto> todos = repository.findByCompleted(true);
        model.addAttribute("todos",todos);
        return "/todos";
    }

    @GetMapping("/todos/detail/{id}/toggle")
    public String toggle(@PathVariable Long id,Model model){
        try{
            TodoDto todo =repository.getById(id).orElseThrow(()->new IllegalArgumentException("!!!"));
            todo.setCompleted(!todo.getCompleted());
            repository.save(todo);
            return "redirect:/todos/detail/"+id;
        }catch (IllegalArgumentException e){
            return "redirect:/todos";
        }

    }

}
