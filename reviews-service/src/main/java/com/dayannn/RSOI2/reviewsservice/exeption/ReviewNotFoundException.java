package com.dayannn.RSOI2.reviewsservice.exeption;

public class ReviewNotFoundException extends Exception{
    public ReviewNotFoundException(Long id){
            super("Review with id= " + id + " was not found!");
        }

}
