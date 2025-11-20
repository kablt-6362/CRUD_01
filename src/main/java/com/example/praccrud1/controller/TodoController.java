package com.example.praccrud1.controller;


import com.example.praccrud1.repository.TodoRepository;
import com.example.praccrud1.tododto.TodoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository repository;
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String todos(Model model){
        List<TodoDto> todos = repository.getAll();
        model.addAttribute("todos",todos);
        return "todos";
    }

    @GetMapping("/new")
    public String newTodo(){
        return "new";
    }
    @PostMapping
    public String create(@RequestParam String title, @RequestParam String content, Model model, RedirectAttributes redirectAttributes){
        TodoDto tododto = new TodoDto(null,title,content,false
        );
        //TodoRepository repository = new TodoRepository();
        redirectAttributes.addFlashAttribute("message","할 일 이 추가되었습니다");
        TodoDto todo = repository.save(tododto);
        model.addAttribute("todo",todo);
        return "redirect:/todos";
    }

    @GetMapping("/detail/{id}")
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Model model,
                         RedirectAttributes redirectAttributes){
        repository.delete(id);
        redirectAttributes.addFlashAttribute("message","할 일이 삭제되었습니다");
        redirectAttributes.addFlashAttribute("status","delete");
        return "redirect:/todos";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable Long id,Model model){
        //TodoDto todo = repository.getById(id);
        try{
         TodoDto todo = repository.getById(id).orElseThrow(()->new IllegalArgumentException("todo not found"));
        model.addAttribute("todo",todo);
        return "update";
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam String content,
                       @RequestParam(defaultValue = "false") Boolean completed,
                       Model model,
                         RedirectAttributes redirectAttributes){
        try{
            TodoDto todo = repository.getById(id).orElseThrow(()->new IllegalArgumentException("todo not found"));
            todo.setTitle(title);
            todo.setContent(content);
            todo.setCompleted(completed);
            repository.save(todo);
            redirectAttributes.addFlashAttribute("message","할 일이 수정되었습니다");
            return "redirect:/todos/detail/"+id;
        }
        catch(IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("message","없는 할 일입니다.");
            return "redirect:/todos";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword,Model model){
        List<TodoDto> todos = repository.findByTitleContaining(keyword);
        model.addAttribute("todos",todos);
        return "todos";
    }

    @GetMapping("/active")
    public String active(Model model){
        List<TodoDto> todos = repository.findByCompleted(false);
        model.addAttribute("todos",todos);
        return "/todos";
    }

    @GetMapping("/completed")
    public String completed(Model model){
        List<TodoDto> todos = repository.findByCompleted(true);
        model.addAttribute("todos",todos);
        return "/todos";
    }

    @GetMapping("/detail/{id}/toggle")
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
