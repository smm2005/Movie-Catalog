package com.moviecatalog.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.moviecatalog.catalog.security.RSAKeyProperties;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class CatalogApplication{

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

}
