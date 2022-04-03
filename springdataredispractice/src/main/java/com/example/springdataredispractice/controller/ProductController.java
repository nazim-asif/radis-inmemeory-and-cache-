package com.example.springdataredispractice.controller;

import com.example.springdataredispractice.entity.Product;
import com.example.springdataredispractice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Nazim Uddin Asif
 * @version: 1.0
 */
@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {
    @Autowired
    private ProductRepository dao;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return dao.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dao.findAll();
    }
    @PutMapping
    @CachePut(key = "#id",value = "Product" )
    public String update(@RequestBody Product product) {
        return dao.updateById(product.getId(), product);
    }
    @GetMapping("/{id}")
//    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 1000")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id) {
        return dao.findProductById(id);
    }
    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id)   {
        return dao.deleteProduct(id);
    }

}
