package com.seazen.sidercar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
public class SiderCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiderCarApplication.class, args);
	}

}
