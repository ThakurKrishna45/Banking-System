package com.krishna.banking;

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
		SpringApplication.run(BankingSystemApplication.class, args);
	}

	}

