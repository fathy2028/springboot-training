package com.users.demo.domian;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String name;
private int price;
private String imagepath;
@ManyToOne
private Category category;
public Product() {
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public int getPrice() {
    return price;
}
public void setPrice(int price) {
    this.price = price;
}

public Category getCategory() {
    return category;
}
public void setCategory(Category category) {
    this.category = category;
}
public String getImagepath() {
    return imagepath;
}
public void setImagepath(String imagepath) {
    this.imagepath = imagepath;
}

}