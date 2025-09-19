package com.xiaoshen.xiaoshenlike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.xiaoshen.xiaoshenlike.mapper")
public class XiaoshenlikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaoshenlikeApplication.class, args);
	}

}
