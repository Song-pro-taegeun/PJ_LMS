package com.pro.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class LmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
		System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("START LMS APPLICATION");
		System.out.println(args);
        System.out.println("------------------------------------------------------------------------------------------------");
	}

	private static long id = System.currentTimeMillis();
	public synchronized static String makeNextSessionId() {
		id++;
		return "PJ_LMS" + id;
	}
}
