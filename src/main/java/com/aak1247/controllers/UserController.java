package com.aak1247.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  aak12 on 2017/5/10.
 */
@RestController
@RequestMapping(value = "/rest/user")
public class UserController {
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity signup(){
        return null;
    }
}
