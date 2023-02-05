package com.in28minutes.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//@Controller
@SessionAttributes("name")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap map){
        String username = getLoggedInUsername(map);
        List<Todo> todos = todoService.findByUsername(username);
        map.addAttribute("todoss",todos);
        return "listTodos";
    }

    private String getLoggedInUsername(ModelMap map){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @RequestMapping(value="addtodo", method= RequestMethod.GET)
    public String showTodo(ModelMap map){
        String username = (String)map.get("name");
        Todo todo = new Todo(0,username,"",LocalDate.now().plusYears(1),false);
        map.put("todo",todo);
        return "todo";
    }

    @RequestMapping(value="addtodo", method= RequestMethod.POST)
    public String addtodo(ModelMap map, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }
        todoService.addTodo((String)map.get("name"),todo.getDescription(), todo.getTargetDate(),false);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id){
        todoService.deleteTodoById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value="update-todo",method= RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap map){
        Todo todo = todoService.findById(id);
        map.addAttribute("todo",todo);
        return "todo";
    }

    @RequestMapping(value="update-todo", method= RequestMethod.POST)
    public String updatetodo(ModelMap map, @Valid Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "todo";
        }
        String username = (String)map.get("name");
        todo.setUsername(username);
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }
}
