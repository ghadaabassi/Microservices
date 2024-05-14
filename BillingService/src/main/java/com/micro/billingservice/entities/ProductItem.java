package com.micro.billingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private Long productID;
    private double price;
    private double quantity;
    @ManyToOne
    private Bill bill;
}

