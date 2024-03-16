package com.users.demo.controller;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.users.demo.domian.user;
import com.users.demo.service.userservice;

import jakarta.servlet.http.HttpSession;

@Controller
public class usercontroller {
    @Autowired
    private userservice userserv;
    @GetMapping("/")
    public ModelAndView homepage(){
        ModelAndView mav=new ModelAndView("home.html");
        return mav;
    }
    @GetMapping("/index")
    public String viewuserspage(Model model){
        List<user> userslist=userserv.listall();
        model.addAttribute("userslist", userslist);
        return "index";
    }
    @GetMapping("/new")
    public ModelAndView add(){
       ModelAndView mav=new ModelAndView("new");
       user u=new user();
       mav.addObject("user", u);
       return mav;
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("user") user u, @RequestParam("file") MultipartFile file ) {
       String encodedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(12));
        u.setPassword(encodedPassword);
        MultipartFile image = file;
        String uploadDir="C:/Users/elsayed/Desktop/repospring/springboot-training/demo/src/main/resources/static/images";
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        try {
            // Save the file to the specified directory
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
               
                Files.copy(inputStream, uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                 u.setImagephoto(fileName);
            }
        } catch (IOException e) {
            // Handle file upload error
            e.printStackTrace();
        }
    
        userserv.saveuser(u);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edituserpage(@PathVariable(name = "id") int id){
        ModelAndView mav= new ModelAndView("new");
        user u=userserv.edituser(id);
        mav.addObject("user", u);
        return mav;
    }
    @GetMapping("/delete/{id}")
    public String deleteuser(@PathVariable(name = "id") int id){
        userserv.deleteuser(id);
        return "redirect:/index";
    }
    @GetMapping("/login")
    public ModelAndView loginForm() {
        ModelAndView mav=new ModelAndView("login.html");
        user u=new user();
        mav.addObject("user", u); 
        return mav;
        // return the login form view
    }

    @PostMapping("/loginprocess")
    public RedirectView logingin(@RequestParam("username") String username, @RequestParam("password") String password , HttpSession session) {
        user userlog = userserv.authintiacate(username);
        if (userlog != null) {
            String dbusername = userlog.getUsername();
            Boolean dbpassword = BCrypt.checkpw(password, userlog.getPassword());
            if (dbusername.equals(username) && dbpassword) {
             session.setAttribute("username", dbusername);
             session.setAttribute("photo", userlog.getImagephoto());
             return new RedirectView("welcome");
            } else {
                return new RedirectView("login");
            }
        } else {
            return new RedirectView("login");
        }
    }
    @GetMapping("/welcome")
    public ModelAndView welcomepage(HttpSession session){
        ModelAndView mav=new ModelAndView("welcome");
        mav.addObject("username", (String) session.getAttribute("username"));
        mav.addObject("photo", (String) session.getAttribute("photo"));
        return mav;
    }
}
