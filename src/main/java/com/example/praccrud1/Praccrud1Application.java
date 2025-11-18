package com.example.praccrud1;

import com.example.praccrud1.repository.TodoRepository;
import com.example.praccrud1.tododto.TodoDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Praccrud1Application {

    public static void main(String[] args) {
        SpringApplication.run(Praccrud1Application.class, args);
    }

    @Bean
    public CommandLineRunner init(TodoRepository todoRepository){
        return args -> {
            todoRepository.save(new TodoDto(null, "study", "JAVA", false));
            todoRepository.save(new TodoDto(null, "cook", "kimbob", false));
            todoRepository.save(new TodoDto(null, "workout", "run", false));
        };
    }


}
