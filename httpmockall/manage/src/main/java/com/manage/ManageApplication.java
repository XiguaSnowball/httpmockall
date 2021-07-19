package com.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({"com.manage.*"})
@MapperScan(value = "com.manage.mappers")
@EnableAsync
public class ManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageApplication.class, args);
	}


}
