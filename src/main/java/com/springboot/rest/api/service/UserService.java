package com.springboot.rest.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.repository.UserRepository;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository repository;

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();


    public boolean saveNewUser(User entry) {
        try{
            entry.setPassword(encoder.encode(entry.getPassword()));
            entry.setRoles(Arrays.asList("USER"));
            repository.save(entry);
            return true;
        } catch (Exception e) {
            log.info("adcac");
           return false;
        }
    }

    public void saveUser(User entry) {
        repository.save(entry);
    }

    public List<User> getAllEntries() {
        return repository.findAll();
    }

    public  Optional<User> getEntryById(ObjectId id) {
        return repository.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        repository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        repository.save(user);
    }

}
