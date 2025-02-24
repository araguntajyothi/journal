package com.springboot.rest.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.api.entity.JournalEntry;
import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.service.JournalEntryService;
import com.springboot.rest.api.service.UserService;

// if we a crud operation as Transactional means, if multiple database calls are happening, if anyone of the call is failing, all of them will be rolled back
// Annotate main class with @EnableTransactionManagement, PlatformTransactionManager(interface) will do the actions like commit and rollback, implementation  for this interface is MongoTransactionManager, to tell this to spring we need to create a bean
// create a bean of PlatformTransactionManager and return MongoTransactionManager
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {


  private JournalEntryService journalService;


  private UserService userService;

    public JournalEntryControllerv2(JournalEntryService journalService, UserService userService) {
        this.journalService = journalService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String userName = auth.getName();

     User user = userService.findByUserName(userName);

     List<JournalEntry> all = user.getJournalEntries();
     if(all != null && !all.isEmpty()){
      return new ResponseEntity<>(all, HttpStatus.OK);
     } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    }

    
    @PostMapping
    public ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry myEntry){
      try {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
      
        journalService.saveEntry(myEntry, userName);
      return new ResponseEntity<>(myEntry, HttpStatus.CREATED);  

     } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
    }
    

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String userName = auth.getName();

      User user = userService.findByUserName(userName);
    List<JournalEntry> collect =  user.getJournalEntries().stream().filter(e -> e.getId().equals(myId)).collect(Collectors.toList());

    if(!collect.isEmpty()){
      Optional<JournalEntry> journalEntry = journalService.getEntryById(myId);
      
      if(journalEntry.isPresent()){
        return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);  
     }
    }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

   

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
      @PathVariable ObjectId myId, 
      @RequestBody JournalEntry newEntry){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> collect =  user.getJournalEntries().stream().filter(e -> e.getId().equals(myId)).collect(Collectors.toList());
      
        if(!collect.isEmpty()){
          Optional<JournalEntry> journalEntry = journalService.getEntryById(myId);
          
          if(journalEntry.isPresent()){
            JournalEntry oldEntry = journalEntry.get();
            if(oldEntry != null){
              oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
              oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
             
              journalService.saveEntry(oldEntry);
              return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
         }
        }

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
     
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?>  deleteJournalEntryById(@PathVariable ObjectId myId){

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String userName = auth.getName();
     boolean removed = journalService.deleteEntryById(myId, userName);

      if(removed){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {  
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

    }


}
