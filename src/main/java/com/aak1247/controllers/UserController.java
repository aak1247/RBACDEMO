package com.aak1247.controllers;

import com.aak1247.models.Passport;
import com.aak1247.models.User;
import com.aak1247.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author  aak12 on 2017/5/10.
 */
@RestController
@RequestMapping(value = "/rest/user")
public class UserController {
    private UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestBody User user){
        userRepository.insert(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ResponseEntity signin(@RequestBody Passport passport, HttpSession httpSession){
        User curUser = userRepository.findOneByUsername(passport.username);
        if (curUser == null||!curUser.isRightPassword(passport.password)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        httpSession.setAttribute("userId",curUser.getUserId());
//        httpSession.setAttribute("role",);
        return null;
    }
}
