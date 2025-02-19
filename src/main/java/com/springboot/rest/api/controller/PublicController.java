package com.springboot.rest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

     @Autowired
  private UserService service;

      @PostMapping
    public ResponseEntity<User>  createEntry(@RequestBody User user){
     try {
      service.saveNewUser(user);
      return new ResponseEntity<>(user, HttpStatus.CREATED);  

     } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
    }

}
