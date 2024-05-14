package com.micro.billingservice;

import com.micro.billingservice.entities.Bill;
import com.micro.billingservice.entities.ProductItem;
import com.micro.billingservice.repositories.BillRepository;
import com.micro.billingservice.repositories.ProdcutItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProdcutItemRepository prdRepository
	) {
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Bill.class);

			Bill bill1 = billRepository.save(new Bill(null,new Date(),1L,null));

			prdRepository.save(new ProductItem(null,1L,800.0,30,bill1));

			prdRepository.save(new ProductItem(null,2L,800.0,30,bill1));
			prdRepository.save(new ProductItem(null,3L,800.0,30,bill1));
		} ;
	}
}

}
