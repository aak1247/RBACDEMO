package com.aak1247.controllers;

import com.aak1247.models.Passport;
import com.aak1247.models.Role;
import com.aak1247.models.User;
import com.aak1247.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            System.out.println(passport.username+","+passport.password);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        httpSession.setAttribute("userId",curUser.getUserId());
//        httpSession.setAttribute("role",);
        return new ResponseEntity<>(curUser, HttpStatus.OK);
    }
    @RequestMapping(value = "/hasRole")
    public ResponseEntity hasRole(@RequestParam User user, HttpSession httpSession){
        if (httpSession.getAttribute("userId")==null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User userFound = userRepository.findOneByUsername(user.getUsername());
        return new ResponseEntity<>(userFound.hasRole(user.getRoleList().get(0)),HttpStatus.OK);
    }


}
