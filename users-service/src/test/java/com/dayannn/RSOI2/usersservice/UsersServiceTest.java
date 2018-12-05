package com.dayannn.RSOI2.usersservice;

import com.dayannn.RSOI2.usersservice.entity.User;
import com.dayannn.RSOI2.usersservice.repository.UsersRepository;
import com.dayannn.RSOI2.usersservice.service.UsersService;
import com.dayannn.RSOI2.usersservice.service.UsersServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class UsersServiceTest {
    private UsersService usersService;

    @Mock
    UsersRepository usersRepository;

    @Before
    public void setUp(){
        initMocks(this);
        usersService = new UsersServiceImplementation(usersRepository);
    }

    @Test
    public void shouldReturnAllUsers(){
        List<User> users= new ArrayList<>();
        User user= new User();
        user.setRating(5);
        user.setLastName("Popovich");
        user.setName("Alesha");
        user.setLogin("alesha994");
        user.setReviewsNum(4);

        given(usersRepository.findAll()).willReturn(users);
        List<User> usersReturned = usersService.getAllUsers();
        assertThat(usersReturned, is(users));
    }

    @Test
    public void shouldCreateUser(){
        User user= new User();
        user.setRating(5);
        user.setLastName("Popovich");
        user.setName("Alesha");
        user.setLogin("alesha994");
        user.setReviewsNum(4);

        given(usersRepository.save(user)).willReturn(user);
        assertThat(usersRepository.save(user), is(user));
    }


}
