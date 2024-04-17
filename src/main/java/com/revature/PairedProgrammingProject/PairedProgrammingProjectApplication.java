package com.revature.PairedProgrammingProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
<<<<<<< HEAD
@ComponentScan("com.revature")
@EntityScan("com.revature.models")
=======
@EntityScan("com.revature.models")
@ComponentScan("com.revature")
>>>>>>> origin/thomas
@EnableJpaRepositories("com.revature.daos")
public class PairedProgrammingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PairedProgrammingProjectApplication.class, args);
	}

}
