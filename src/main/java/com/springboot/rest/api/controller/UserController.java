package com.springboot.rest.api.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.repository.UserRepository;
import com.springboot.rest.api.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
  private UserService service;

   @Autowired
    private UserRepository repository;


   

    

     @PutMapping
    public ResponseEntity<?> updateJournalEntryById(@RequestBody User user){
      
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String userName = auth.getName();

    User userNameInDB =  service.findByUserName(userName);

      userNameInDB.setUserName(user.getUserName());
      userNameInDB.setPassword(user.getPassword());
      service.saveNewUser(userNameInDB);
   
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

// @DeleteMapping("/id/{myId}")
//     public ResponseEntity<?>  deleteJournalEntryById(@PathVariable ObjectId myId){

//        service.deleteEntryById(myId);

//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

//     }

@DeleteMapping
    public ResponseEntity<?>  deleteJournalEntryById(@PathVariable ObjectId myId){
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      
      repository.deleteByUserName(auth.getName());

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }



}
