package com.springboot.rest.api.controller;

import java.util.List;

import com.springboot.rest.api.cache.AppCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

     
      private final UserService service;

      private final AppCache appCache;

    public AdminController(UserService service, AppCache appCache) {
        this.service = service;
        this.appCache = appCache;
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAll(){
        List<User> allUsers = service.getAllEntries();

        if(allUsers != null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
            
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public  void createAdminUser(@RequestBody User user){
        service.saveAdmin(user);
    }

    @GetMapping("clear-app-cache")
    public void clearCacheApp(){
        appCache.init();
    }

}
