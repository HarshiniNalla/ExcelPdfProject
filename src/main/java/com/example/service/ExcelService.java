package com.example.service;

import com.example.entity.Product;
import com.example.model.ExcelFormat;
import com.example.repository.ProductRepository;

import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {


    private ProductRepository productRepo;
    public ExcelService(ProductRepository productRepo)
    {
        this.productRepo=productRepo;
    }

    public ByteArrayInputStream  getActualData() throws IOException {
        List<Product> all=productRepo.findAll();
        return ExcelFormat.dataToExcel(all);


    }


}
