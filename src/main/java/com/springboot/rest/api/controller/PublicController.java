package com.springboot.rest.api.controller;

import com.springboot.rest.api.service.UserDetailsServiceImpl;
import com.springboot.rest.api.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.service.UserService;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {


  private UserService service;


  private AuthenticationManager authenticationManager;


  private UserDetailsServiceImpl userDetailsService;


  private JwtUtil jwtUtil;


    public PublicController(UserService service, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<User>  createEntry(@RequestBody User user) {
          try {
              service.saveNewUser(user);
              return new ResponseEntity<>(user, HttpStatus.CREATED);

          } catch (Exception e) {
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
      }

          @PostMapping("/login")
          public ResponseEntity<String> login(@RequestBody User user) {
              try {
                 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

                  UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());

                String jwt =  jwtUtil.generateToken(userDetails.getUsername());

                return new ResponseEntity<>(jwt, HttpStatus.OK);

              } catch (Exception e) {
                  return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
              }
          }


}
