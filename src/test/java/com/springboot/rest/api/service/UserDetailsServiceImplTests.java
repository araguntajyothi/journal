//package com.springboot.rest.api.service;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import static org.mockito.Mockito.when;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.mongodb.assertions.Assertions;
//import com.springboot.rest.api.entity.User;
//import com.springboot.rest.api.repository.UserRepository;
//
//// @SpringBootTest
//@ActiveProfiles("dev")
//public class UserDetailsServiceImplTests {
//
//   @InjectMocks
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Disabled
//    @Test
//    void loadUserByUsernameTest(){
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("inrinrick").roles(new ArrayList<>()).build());
//        UserDetails user = userDetailsService.loadUserByUsername("ram");
//        Assertions.assertNotNull(user);
//    }
//
//
//
//}
