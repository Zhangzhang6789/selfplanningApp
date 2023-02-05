package com.in28minutes.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.cglib.core.Local;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int todoCount = 0;

    static  {
        todos.add(new Todo(++todoCount,"in28minutes","learn AWS1",
                LocalDate.now().plusYears(1),false));
        todos.add(new Todo(++todoCount,"in28minutes","learn DevOps1",
                LocalDate.now().plusYears(2),false));
        todos.add(new Todo(++todoCount,"in28minutes","learn Full Stack Development1",
                LocalDate.now().plusYears(3),false));
    }

    public List<Todo> findByUsername(String username){
        java.util.function.Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
        return todos.stream().filter(predicate).toList();
    }

    public void addTodo(String username, String description, LocalDate targetDate, boolean done){
        Todo todo = new Todo(++todoCount,username,description,targetDate,done);
        todos.add(todo);
    }

    public void deleteTodoById(int id){
        java.util.function.Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        todos.removeIf(predicate);
    }

    public Todo findById(int id){
        java.util.function.Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        Todo todo = todos.stream().filter(predicate).findFirst().get();
        return todo;
    }

    public void updateTodo(@Valid Todo todo) {
        deleteTodoById(todo.getId());
        todos.add(todo);
    }
}
