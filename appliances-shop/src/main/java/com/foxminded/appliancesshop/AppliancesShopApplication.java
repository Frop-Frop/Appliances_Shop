package com.foxminded.appliancesshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "Appliances shop API", version = "1.0", description = "Made for Foxminded course", termsOfService = "https://www.apache.org/licenses/LICENSE-2.0", contact = @Contact(name = "Sofia Kim", email = "sophianpilova@gmail.com"), license = @License(name = "Apache license Version 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
@SpringBootApplication
public class AppliancesShopApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppliancesShopApplication.class, args);
	}

}
