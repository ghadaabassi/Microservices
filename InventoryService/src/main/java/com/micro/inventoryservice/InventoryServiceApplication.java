package com.micro.inventoryservice;

import com.micro.inventoryservice.entities.Product;
import com.micro.inventoryservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository prdRepository) {
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Product.class);

			//prdRepository.save(new Product(null,"Ordinateur",1500));
			//prdRepository.save(new Product(null,"Imprimante",250));
			//prdRepository.findAll().forEach(System.out::println);
		};
	}

}
