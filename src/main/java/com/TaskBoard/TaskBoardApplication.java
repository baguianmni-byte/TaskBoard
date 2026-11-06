package com.TaskBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Point de départ de l'application Spring Boot. */
@SpringBootApplication
public class TaskBoardApplication {

	/** Démarre Spring et tous les composants de l'application. */
	public static void main(String[] args) {
		SpringApplication.run(TaskBoardApplication.class, args);
	}

}
