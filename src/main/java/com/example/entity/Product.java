package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="dataproducts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String productName;
    private int price;
    private int quantity;
    private String city;
    private int pincode;
}
