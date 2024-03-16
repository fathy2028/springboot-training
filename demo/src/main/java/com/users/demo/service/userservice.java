package com.users.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.users.demo.domian.user;
import com.users.demo.repository.userrepository;

@Service
public class userservice {
    @Autowired
    private userrepository userrepo;
   public List<user> listall(){
    return userrepo.findAll();
   }
   public void saveuser(user u){
    userrepo.save(u);
   }
   public user edituser(long id){
    return userrepo.findById(id).get();
   }
   public void deleteuser(long id){
    userrepo.deleteById(id);
   }
   public user authintiacate(String username){
    user usercheck=userrepo.findByUsername(username);
    return usercheck;
   }
}
