package com.krishna.banking;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class BankingSystemApplication {
//	static {
//		java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Kolkata"));
//	}
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
		SpringApplication.run(BankingSystemApplication.class, args);
	}

	}

