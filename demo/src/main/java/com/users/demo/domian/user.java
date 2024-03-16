package com.users.demo.domian;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class user {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String username;
private String password;
private String faculty;
private int age;
private String imagephoto;
public user() {
    super();
}
public user(long id, String username, String password, String faculty, int age) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.faculty = faculty;
    this.age = age;
}
public long getId() {
    return id;
}
public void setId(long id) {
    this.id = id;
}
public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public String getFaculty() {
    return faculty;
}
public void setFaculty(String faculty) {
    this.faculty = faculty;
}
public int getAge() {
    return age;
}
public void setAge(int age) {
    this.age = age;
}
public String getImagephoto() {
    return imagephoto;
}
public void setImagephoto(String imagephoto) {
    this.imagephoto = imagephoto;
}    
}
