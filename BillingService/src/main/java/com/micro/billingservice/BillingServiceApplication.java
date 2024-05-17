package com.micro.billingservice;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	private Date billingDate;
	private Long customerID;
	@OneToMany(mappedBy = "bill")
	private Collection<ProductItem> productItems=null;

	@Transient
	private Customer customer;


}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class ProductItem{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	private Long productID;
	private double price;
	private double quantity;
	@ManyToOne
	private Bill bill;
}

@Data
class Customer{
	private Long id;
	private String name;
	private String email;
}

@FeignClient(name="CUSTOMER-SERVICE")
interface CustomerService{
	@GetMapping("/customers/{id}")
	Customer findCustomerById(@PathVariable(name="id") Long id);
}

@Data
class Product{
	private Long id;
	private String name;
	private double price;
}

@FeignClient(name="InventoryService")
interface ProductService{
	@GetMapping("/products/{id}")
	Product findProductById(@PathVariable(name="id") Long id);

}




@RepositoryRestResource
interface  BillRepository extends JpaRepository<Bill, Long> {

}


@RepositoryRestResource
interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}






@RestController
class BillRestController{
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductItemRepository prodcutItemRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@GetMapping(path="/dataBill/{id}")
	public Bill getBill(@PathVariable(name="id") Long id) {
		Bill bill=billRepository.findById(id).get();
		bill.setCustomer(customerService.findCustomerById(bill.getCustomerID()));

		bill.getProductItems().forEach(pi->{
			pi.setProductID(pi.getProductID());
		});

		return bill;
	}
}







@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}


	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository prdRepository,
							CustomerService customerservice,
							ProductService productservice
	) {
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Bill.class);

//			Bill bill1 = billRepository.save(new Bill(null,new Date(),1L,null));
//
//			prdRepository.save(new ProductItem(null,1L,800.0,30,bill1));
//
//			prdRepository.save(new ProductItem(null,2L,800.0,30,bill1));
//			prdRepository.save(new ProductItem(null,3L,800.0,30,bill1));

			//******************************************

//			Customer c1 = customerservice.findCustomerById(1L);
//			Bill bill1 = billRepository.save(new Bill(null,new Date(),c1.getId(),null));
//			System.out.println(c1.getName());
//			System.out.println(c1.getEmail());
//
//			prdRepository.save(new ProductItem(null,1L,800.0,30,bill1));
//			prdRepository.save(new ProductItem(null,2L,800.0,30,bill1));
//			prdRepository.save(new ProductItem(null,3L,800.0,30,bill1));

			Collection<ProductItem> productItems=null;

			Customer c1 = customerservice.findCustomerById(1L);
			System.out.println(c1.getId());
			System.out.println(c1.getName());
			System.out.println(c1.getEmail());

			Bill bill1= billRepository.save(new Bill(null,new Date(),c1.getId(),null,null));
			Product p1 = productservice.findProductById(1L);
			prdRepository.save(new ProductItem(null,p1.getId(),p1.getPrice(),30,bill1));

			Product p2 = productservice.findProductById(2L);
			prdRepository.save(new ProductItem(null,p2.getId(),p2.getPrice(),20,bill1));


		} ;
	}


}
