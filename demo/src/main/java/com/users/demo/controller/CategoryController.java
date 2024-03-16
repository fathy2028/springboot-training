package com.users.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.users.demo.domian.Category;
import com.users.demo.domian.Product;
import com.users.demo.repository.CategoryRepository;
import com.users.demo.repository.ProductRepository;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Controller
public class CategoryController { 
@Autowired
private CategoryRepository categoryRepository;

@Autowired
private ProductRepository productRepository;
@GetMapping("/allcategories")
public ModelAndView getCategories() {
ModelAndView mav = new ModelAndView("allcategories.html"); 
List<Category> categories = this.categoryRepository.findAll(); 
mav.addObject("categories", categories);
return mav;
}

@GetMapping("/addcategory")
public ModelAndView addCategory() {
ModelAndView mav = new ModelAndView("addcategory.html"); 
Category newCategory = new Category();
mav.addObject("category", newCategory);
return mav;
}
@PostMapping("/savecategory")
public String saveCategory (@ModelAttribute("category") Category category) {
this.categoryRepository.save(category);
return "redirect:/allcategories";
}
@GetMapping("/editcategory/{id}")
public ModelAndView editcategorypage(@PathVariable(name = "id") int id){
    ModelAndView mav= new ModelAndView("addcategory.html");
    Category category= this.categoryRepository.findById(id).get();
    mav.addObject("category", category);
    return mav;
}

@GetMapping("/addproduct")
public ModelAndView addproduct() {
ModelAndView mav = new ModelAndView("addproduct.html"); 
List<Category> allcategories = this.categoryRepository.findAll(); 
mav.addObject("allCategories", allcategories);
Product newproduct = new Product();
mav.addObject( "product", newproduct);
return mav;
}
@PostMapping("/saveproduct")
public String saveproduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) {
    MultipartFile image = file;
    String uploadDir="C:/Users/elsayed/Desktop/reg and login by spring/demo/src/main/resources/static/images";
    
    String fileName = StringUtils.cleanPath(image.getOriginalFilename());
    try {
        // Save the file to the specified directory
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = image.getInputStream()) {
           
            Files.copy(inputStream, uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
             product.setImagepath(fileName);
        }
    } catch (IOException e) {
        // Handle file upload error
        e.printStackTrace();
    }

    // Save the product object to the database
    productRepository.save(product);

    return "redirect:/allcategories";
}
@GetMapping("/editproduct/{id}")
public ModelAndView editproductpage(@PathVariable(name = "id") int id){
    ModelAndView mav= new ModelAndView("addproduct.html");
    List<Category> allcategories = this.categoryRepository.findAll(); 
    mav.addObject("allCategories", allcategories);
    Product product= this.productRepository.findById(id).get();
    mav.addObject("product", product);
    return mav;
}
@GetMapping("/category/{id}")
public ModelAndView getCategory(@PathVariable("id") int id) { 
    ModelAndView mav = new ModelAndView("allproducts.html");
List<Product> products=this.productRepository.findAllByCategoryId(id); 
mav.addObject( "products", products);
return mav;
}
@GetMapping("/deletecateogory/{id}")
public String deletecategory(@PathVariable(name = "id") int id){
   this.categoryRepository.deleteById(id);
    return "redirect:/allcategories";
}
@GetMapping("/deleteproduct/{id}")
public String deleteproduct(@PathVariable(name = "id") int id){
   this.productRepository.deleteById(id);
    return "redirect:/allcategories";
}
}
