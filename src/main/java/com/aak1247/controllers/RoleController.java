package com.aak1247.controllers;

import com.aak1247.models.Role;
import com.aak1247.models.User;
import com.aak1247.repositories.RoleRepository;
import com.aak1247.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
@RequestMapping("/rest/role")
public class RoleController {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    @Autowired
    public RoleController(RoleRepository roleRepository,
                          UserRepository userRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addRole(@RequestBody Role role, HttpSession httpSession){
        if (httpSession.getAttribute("userId")==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        String roleId = httpSession.getAttribute("role").toString();
        if (roleId==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role currRole = roleRepository.findOne(roleId);
        if (currRole==null||!currRole.getRoleName().equals("admin"))return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        roleRepository.save(role);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/changeRole",method = RequestMethod.POST)
    public ResponseEntity changeCurRole(@RequestBody Role role, HttpSession httpSession){
        if (httpSession.getAttribute("userId")==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = userRepository.findOne(httpSession.getAttribute("userId").toString());
        if (user==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role roleFound = roleRepository.findOneByRoleName(role.getRoleName());
        if (roleFound==null)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!user.getRoleList().contains(roleFound.getRoleId()))return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        httpSession.setAttribute("role",roleFound.getRoleId());
        return new ResponseEntity<>(role,HttpStatus.OK);
    }


}
