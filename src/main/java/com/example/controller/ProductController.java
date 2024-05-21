package com.example.controller;

import com.example.dto.ProductDto;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.ExcelService;
import com.example.service.PdfService;
import com.example.service.ProductService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {




    private final ProductService productservice;
    private final ExcelService excelService;

    private ProductRepository productRepo;
    public ProductController(ProductRepository productRepo,ExcelService excelService,ProductService productService) {
        this.productRepo = productRepo;

        this.excelService=excelService;
        this.productservice=productService;
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productdto) {


        Product product = new Product();

        product.setProductName(productdto.getProductName());
        product.setCity(productdto.getCity());
        product.setPrice(productdto.getPrice());
        product.setPincode(productdto.getPincode());
        product.setQuantity(productdto.getQuantity());

        Product savedProduct = productservice.saveProduct(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    @GetMapping("/fetchAll")
    public ResponseEntity<List<Product>> getAllProducts() {

        List<Product> getproducts = productservice.fetchAllProducts();
        return new ResponseEntity<>(getproducts, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcel() throws IOException {
        String filename = "products.xlsx";
        ByteArrayInputStream actualData = excelService.getActualData();
        InputStreamResource file = new InputStreamResource(actualData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @GetMapping(value = "/openpdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> productReport() {
        List<Product> products = productRepo.findAll();
        ByteArrayInputStream bis = PdfService.productsPDFReport(products);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=products.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}