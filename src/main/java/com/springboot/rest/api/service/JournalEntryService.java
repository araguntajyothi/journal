package com.springboot.rest.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.api.entity.JournalEntry;
import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository repository;

    @Autowired
    private UserService service;

   private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);


    @Transactional
    public void saveEntry(JournalEntry entry, String userName) {
        
       try {
        User user = service.findByUserName(userName);
        entry.setDate(LocalDateTime.now());
      JournalEntry savedEntry = repository.save(entry);
      user.getJournalEntries().add(savedEntry);
      service.saveUser(user);
           
       } catch (Exception e) {

        throw new RuntimeException(
            "JournalEntryService.saveEntry: " + e.getMessage()
        );
       }}
    

    public void saveEntry(JournalEntry entry) {
        repository.save(entry);
        
    }

    public List<JournalEntry> getAllEntries() {
        return repository.findAll();
    }

    public  Optional<JournalEntry> getEntryById(ObjectId id) {
        return repository.findById(id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id, String userName) {
      boolean removed = false;
      try {
        User user = service.findByUserName(userName);
         removed =  user.getJournalEntries().removeIf(e -> e.getId().equals(id));
        if(removed) {
          service.saveUser(user);
          repository.deleteById(id);
        }

        return removed;
          
      } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException(
            "JournalEntryService.deleteEntryById: " + e.getMessage()
        );
      }}
    }

  

    

