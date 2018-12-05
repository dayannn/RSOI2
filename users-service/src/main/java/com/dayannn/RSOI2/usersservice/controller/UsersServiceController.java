package com.dayannn.RSOI2.usersservice.controller;

import com.dayannn.RSOI2.usersservice.entity.User;
import com.dayannn.RSOI2.usersservice.exception.UserNotFoundException;
import com.dayannn.RSOI2.usersservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersServiceController {
    private UsersService usersService;

    @Autowired
    UsersServiceController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping(value = "/users")
    public void createUser(@RequestBody User user){
        usersService.createUser(user);
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        return usersService.findUserById(id);
    }

    @GetMapping(value = "/users/find")
    public User getUserByLogin(@RequestParam(value = "login") String login) throws UserNotFoundException {
        return usersService.findUserByLogin(login);
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
