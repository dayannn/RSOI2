package com.dayannn.RSOI2.usersservice.service;

import com.dayannn.RSOI2.usersservice.entity.User;
import com.dayannn.RSOI2.usersservice.exception.UserNotFoundException;

import java.util.List;

public interface UsersService {
    User findUserById(Long id) throws UserNotFoundException;
    User findUserByLogin(String login) throws UserNotFoundException;
    List<User> getAllUsers();
    void createUser(User user);
    void deleteUser(Long id);
    void setRating(Long id, Integer rating) throws UserNotFoundException;
    void increaseRating(Long id, Integer rating) throws UserNotFoundException;
    void increaseRating(Long id) throws UserNotFoundException;
    void decreaseRating(Long id, Integer rating) throws UserNotFoundException;
    void decreaseRating(Long id) throws UserNotFoundException;
}

