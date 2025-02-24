package com.springboot.rest.api.controller;

import com.springboot.rest.api.response.WeatherResponse;
import com.springboot.rest.api.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.repository.UserRepository;
import com.springboot.rest.api.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {


    private UserService service;


    private UserRepository repository;


    private WeatherService weatherService;

    public UserController(UserService service, UserRepository repository, WeatherService weatherService) {
        this.service = service;
        this.repository = repository;
        this.weatherService = weatherService;
    }

    @PutMapping
    public ResponseEntity<?> updateJournalEntryById(@RequestBody User user) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        User userNameInDB = service.findByUserName(userName);

        userNameInDB.setUserName(user.getUserName());
        userNameInDB.setPassword(user.getPassword());
        service.saveNewUser(userNameInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        repository.deleteByUserName(auth.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        System.out.println(weatherResponse.toString());
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + auth.getName() + greeting, HttpStatus.OK);

    }

}
