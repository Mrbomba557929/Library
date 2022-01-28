package com.vyatsu.task14.services;

import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.repositories.ProductRepository;
import javafx.scene.control.DialogPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.vyatsu.task14.repositories.ProductSpecs;

import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

@Service
public class ProductsService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }

    public void saveOrUpdate(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) { productRepository.deleteById(id); }

    public Page<Product> getProductsWithPagingAndFiltering(String word, BigDecimal min, BigDecimal max, PageRequest pageable) {
        Specification<Product> specification = Specification.where(null);

        if (word != null) {
            specification = specification.and(ProductSpecs.titleContainsWord(word));
        }
        if (min != null) {
            specification = specification.and(ProductSpecs.priceGreaterThanOrEq(min));
        }
        if (max != null) {
            specification = specification.and(ProductSpecs.priceLesserThanOrEq(max));
        }

        return productRepository.findAll(specification, pageable);
    }

    public void incrementView(Product product) {
        product.setView(product.getView() + 1);
        saveOrUpdate(product);
    }

//    public List<Product> getTop(){
//        return ((List<Product>) productRepository.findAll()).stream().sorted((o1, o2) -> o2.getView()-o1.getView()).limit(3).collect(Collectors.toList());
//    }
}

