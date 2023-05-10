package com.jobFindingProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Job Finding Project API", version = "1.0", description = "Main API Information"))
public class JobFindingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobFindingProjectApplication.class, args);
	}

}
