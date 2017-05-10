package com.aak1247.controllers;

import com.aak1247.models.Resource;
import com.aak1247.models.Role;
import com.aak1247.models.User;
import com.aak1247.repositories.ResourceRepository;
import com.aak1247.repositories.RoleRepository;
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
@RequestMapping("/rest/resource")
public class ResourceController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ResourceRepository resourceRepository;
    @Autowired
    public ResourceController(UserRepository userRepository,
                              RoleRepository roleRepository,
                              ResourceRepository resourceRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.resourceRepository = resourceRepository;
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity addResource(@RequestBody Resource resource, HttpSession httpSession){
        if (httpSession.getAttribute("userId")==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = userRepository.findOne(httpSession.getAttribute("userId").toString());
        if (user == null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role role = roleRepository.findOne(httpSession.getAttribute("role").toString());
        return null;
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity getResource(@RequestParam String id, HttpSession httpSession){
        if (httpSession.getAttribute("userId")==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = userRepository.findOne(httpSession.getAttribute("userId").toString());
        if (user == null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Role role = roleRepository.findOne(httpSession.getAttribute("role").toString());
        if (role==null)return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (!role.getResourceList().contains(id))return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Resource resource = resourceRepository.findOneById(id);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
