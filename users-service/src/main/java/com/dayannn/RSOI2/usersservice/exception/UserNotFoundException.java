package com.dayannn.RSOI2.usersservice.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Long id){
        super("User with id= " + id + " was not found!");
    }

    public UserNotFoundException(String login){
        super("User with login= " + login + " was not found!");
    }
}
