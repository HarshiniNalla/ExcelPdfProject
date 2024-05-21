package com.example.service;


import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productrepo;


    public ProductService(ProductRepository productrepo) {
        this.productrepo = productrepo;
    }

    public List<Product> fetchAllProducts() {
        return productrepo.findAll();
    }
    public Product saveProduct(Product product)
    {
        return productrepo.save(product);
    }




}
