package com.vyatsu.task14.controllers;

import org.springframework.data.domain.PageRequest;
import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private ProductsService productsService;

    long b = 3;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Model model,
                                   @RequestParam(value = "page",required = false) Integer page,
                                   @RequestParam(value = "word",required = false) String word,
                                   @RequestParam(value = "min",required = false) BigDecimal min,
                                   @RequestParam(value = "max",required = false) BigDecimal max) {
        Product product = new Product();
        if(page == null)
        {
            page = 1;
        }
   //     model.addAttribute("top3products", productsService.getTop());
        model.addAttribute("products", productsService.getProductsWithPagingAndFiltering(word, min, max,PageRequest.of(page-1, 5)).getContent());
        model.addAttribute("product", product);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("word", word);
        return "products";
    }

    @GetMapping("/add")
    public String showProductsById(Model model)
    {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product-edit";
    }


    @GetMapping("/info")
    public String showInfoPage() { return "info"; }


    @GetMapping("/edit/{id}")
    @Secured(value = "ROLE ADMIN")
    public String showEditProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @PostMapping("/add")
    @Secured(value = "ROLE ADMIN")
    public String AddProduct(Model model, @PathVariable(value = "product") Product product) {
        productsService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    @Secured(value = "ROLE ADMIN")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        productsService.incrementView(product);
        model.addAttribute("product", product);
      //  model.addAttribute("top3products", productsService.getTop());
        return "product-page";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(value = "id") Long id) {
        productsService.deleteById(id);
        return "redirect:/products";
    }

}
