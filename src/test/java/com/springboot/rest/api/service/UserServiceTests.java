package com.springboot.rest.api.service;

import com.springboot.rest.api.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.rest.api.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository repository;


    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    // @CsvSource({
//    @ValueSource(strings = {
//        "jyothi",
//        "akshaya",
//        "kiran"
//    })
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user){
     
         assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
        "1,2,3",
        "3,2,5",
        "2,2,4"
    })
    public void test(int a, int b, int expected){
          
        assertEquals(expected, a + b);
    }
}


