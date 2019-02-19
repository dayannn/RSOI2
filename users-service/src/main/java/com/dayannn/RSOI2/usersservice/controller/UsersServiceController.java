package com.dayannn.RSOI2.usersservice.controller;

import com.dayannn.RSOI2.usersservice.entity.User;
import com.dayannn.RSOI2.usersservice.exception.UserNotFoundException;
import com.dayannn.RSOI2.usersservice.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UsersServiceController {
    private UsersService usersService;
    private Logger logger = LoggerFactory.getLogger(UsersServiceController.class);

    @Autowired
    UsersServiceController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping(value = "/users")
    public void createUser(@RequestBody User user){
        usersService.createUser(user);
        logger.info("[POST] /users", user);
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers(){
        logger.info("[GET] /users");
        return usersService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        logger.info("[GET] /users/" + id);
        return usersService.findUserById(id);
    }

    @GetMapping(value = "/users/find")
    public User getUserByLogin(@RequestParam(value = "login") String login) throws UserNotFoundException {
        logger.info("[GET] /users/find ", login);
        return usersService.findUserByLogin(login);
    }

    @DeleteMapping(value = "users/{id}")
    public void deleteUserById(@PathVariable Long id){
        logger.info("[DELETE] /users/" + id);
        usersService.deleteUser(id);
    }

    @GetMapping(value = "/healthcheck")
    public ResponseEntity healthCheck(){
        logger.info("[GET] /healthcheck");
        return usersService.healthCheck();
    }

//    @PostMapping(value = "users/{id}/upvote")
//    public void increaseUserRating(@PathVariable Long id,
//                                   @RequestParam(value = "points", required = false) Integer points)
//            throws UserNotFoundException {
//
//        if (points == null)
//            usersService.increaseRating(id);
//        else
//            usersService.increaseRating(id, points);
//    }
//
//    @PostMapping(value = "users/{id}/downvote")
//    public void decreaseUserRating(@PathVariable Long id,
//                                   @RequestParam(value = "points", required = false) Integer points)
//            throws UserNotFoundException {
//        if (points == null)
//            usersService.decreaseRating(id);
//        else
//            usersService.decreaseRating(id, points);
//    }

//    @PutMapping(value = "users/{id}/rating", consumes = "application/json")
//    public void setUserRating(@PathVariable Long id, @RequestBody Integer points) throws UserNotFoundException {
//        usersService.setRating(id, points);
//    }


}
