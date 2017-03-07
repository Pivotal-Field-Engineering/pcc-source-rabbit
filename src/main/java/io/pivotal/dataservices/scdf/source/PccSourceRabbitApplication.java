package io.pivotal.dataservices.scdf.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(io.pivotal.dataservices.scdf.source.config.GemfireSourceConfiguration.class)
public class PccSourceRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(PccSourceRabbitApplication.class, args);
	}
}
