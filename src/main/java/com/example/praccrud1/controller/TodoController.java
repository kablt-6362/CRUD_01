package com.example.praccrud1.controller;


import com.example.praccrud1.repository.TodoRepository;
import com.example.praccrud1.service.TodoService;
import com.example.praccrud1.tododto.TodoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String todos(Model model){
        List<TodoDto> todos = todoService.getAll();
        model.addAttribute("todos",todos);
        return "todos";
    }

    @GetMapping("/new")
    public String newTodo(Model model){
        model.addAttribute("todo",new TodoDto());
        return "form";
    }
    @PostMapping()
    public String create(
                         @ModelAttribute TodoDto todo,
                         RedirectAttributes redirectAttributes){
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("message","할 일 이 추가되었습니다");
        return "redirect:/todos";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id,Model model){
//        TodoDto todo = repository.getById(id);
        try{
            TodoDto todo = todoService.getById(id);
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
        todoService.delete(id);
        redirectAttributes.addFlashAttribute("message","할 일이 삭제되었습니다");
        redirectAttributes.addFlashAttribute("status","delete");
        return "redirect:/todos";
    }

    @GetMapping("/update/{id}")
    public String edit(@PathVariable Long id,Model model){
        //TodoDto todo = repository.getById(id);
        try{
         TodoDto todo = todoService.getById(id);
        model.addAttribute("todo",todo);
        return "form";
        }catch(IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                       Model model,
                         @ModelAttribute TodoDto todo,
                         RedirectAttributes redirectAttributes){
        try{
            todo.setId(id);
            todoService.save(todo);
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
        List<TodoDto> todos = todoService.findByTitleContaining(keyword);
        model.addAttribute("todos",todos);
        return "todos";
    }

    @GetMapping("/active")
    public String active(Model model){
        List<TodoDto> todos = todoService.findByCompleted(false);
        model.addAttribute("todos",todos);
        return "/todos";
    }

    @GetMapping("/completed")
    public String completed(Model model){
        List<TodoDto> todos = todoService.findByCompleted(true);
        model.addAttribute("todos",todos);
        return "/todos";
    }

    @GetMapping("/detail/{id}/toggle")
    public String toggle(@PathVariable Long id,Model model){
        try{
            TodoDto todo =todoService.getById(id);
            todo.setCompleted(!todo.getCompleted());
            todoService.save(todo);
            return "redirect:/todos/detail/"+id;
        }catch (IllegalArgumentException e){
            return "redirect:/todos";
        }

    }

}
