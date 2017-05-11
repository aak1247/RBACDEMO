package com.aak1247.controllers;

import com.aak1247.models.Passport;
import com.aak1247.models.Role;
import com.aak1247.models.User;
import com.aak1247.repositories.RoleRepository;
import com.aak1247.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author aak12 on 2017/5/10.
 */
@RestController
@RequestMapping(value = "/rest/user")
public class UserController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestBody User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        System.out.println(user.getUsername() + "signup");
        userRepository.insert(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ResponseEntity signin(@RequestBody Passport passport, HttpSession httpSession) {
        System.out.println(passport.username + "signin");
        User curUser = userRepository.findOneByUsername(passport.username);
        if (curUser == null || !curUser.isRightPassword(passport.password)) {
            System.out.println(passport.username + "," + passport.password);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        httpSession.setAttribute("userId", curUser.getUserId());
        httpSession.setAttribute("role", roleRepository.findOneByRoleName("user").getRoleId());
        return new ResponseEntity<>(curUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/hasSign", method = RequestMethod.GET)
    public ResponseEntity hasSign(HttpSession httpSession) {
        System.out.println(httpSession.getAttributeNames());
        System.out.println(httpSession.getAttribute("userId"));
        System.out.println(httpSession.getAttribute("role"));
        boolean flag = httpSession.getAttribute("userId") != null;
        System.out.println(flag);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    @RequestMapping(value = "/hasRole", method = RequestMethod.GET)
    public ResponseEntity hasRole(@RequestParam String roleName, HttpSession httpSession) {
        if (httpSession.getAttribute("userId") == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userRepository.findOne(httpSession.getAttribute("userId").toString());
        if (user == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(user.hasRole(roleRepository.findOneByRoleName(roleName).getRoleId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/manage", method = RequestMethod.POST)
    public ResponseEntity manage(@RequestBody User user, HttpSession httpSession) {
        if (httpSession.getAttribute("userId") == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        String roleId = httpSession.getAttribute("role").toString();
        if (roleId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role currRole = roleRepository.findOne(roleId);
        if (currRole == null || !currRole.getRoleName().equals("admin"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User userFound = userRepository.findOne(user.getUserId());
        userFound.getRoleList().addAll(user.getRoleList());
        userRepository.save(userFound);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/curRole")
    public ResponseEntity getCurRole(HttpSession httpSession) {
//        if (httpSession.getAttribute("userId") == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        String roleId = httpSession.getAttribute("role").toString();
        if (roleId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role currRole = roleRepository.findOne(roleId);
        System.out.println(currRole.getRoleName());
        return new ResponseEntity<>(currRole, HttpStatus.OK);
    }

    @RequestMapping("/showUsers")
    public ResponseEntity showUsers(HttpSession httpSession) {
        if (httpSession.getAttribute("userId") == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        String roleId = httpSession.getAttribute("role").toString();
        if (roleId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role currRole = roleRepository.findOne(roleId);
        if (currRole == null || !currRole.getRoleName().equals("admin"))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public ResponseEntity init(HttpSession httpSession) {
        httpSession.setAttribute("role", roleRepository.findOneByRoleName("guest").getRoleId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
