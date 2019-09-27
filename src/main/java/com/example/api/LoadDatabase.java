package com.example.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.api.jpa.EmployeeRepository;
import com.example.api.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Configuration
//@Slf4j
public class LoadDatabase {
	
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository) {
		return args ->{
			repository.save(new Employee("Antonio", "admin"));
			repository.save(new Employee("Carolina", "user"));
//			log.info("Preloading "+repository.save(new Employee("Antonio", "admin")));
//			log.info("Preloading "+repository.save(new Employee("Carolina", "user")));
		};
	}

}
