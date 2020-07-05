package com.visiblethread.docanalyzer.controller;

import com.visiblethread.docanalyzer.exception.UserNotFoundException;
import com.visiblethread.docanalyzer.model.User;
import com.visiblethread.docanalyzer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/user/{email}")
    public User getUserByEmail(@Length(min = 1) @PathVariable("email") String email) throws UserNotFoundException {
        return userService.findByEmail(email);
    }

    @GetMapping(value = "/user/upload")
    public List<User> filterUsers(@RequestParam(value = "fromDate", required = true) Date fromDate,
                                  @RequestParam(value = "toDate", required = false) Date toDate,
                                  @RequestParam(value = "upload", required = false, defaultValue = "true") boolean upload) {

        return userService.filterUsers(fromDate, toDate, upload);
    }

}
