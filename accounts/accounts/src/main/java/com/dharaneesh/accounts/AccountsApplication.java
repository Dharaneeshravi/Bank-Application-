package com.dharaneesh.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice REST API Documentation",
				version = "v1",
				description = "REST API for IOB Bank",

				contact = @Contact(
						name = "Development Team",
						email = "developer@example.com",
						url = "https://example.com"
				),

				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs =@ExternalDocumentation(
				description = "IOB Account Microservice REST API Documentation",
				url = "https://iob"
)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
