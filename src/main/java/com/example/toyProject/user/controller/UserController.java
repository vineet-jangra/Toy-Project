package com.example.toyProject.user.controller;

import com.example.toyProject.user.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(@RequestBody User user) {
        return userService.loginUser(user);
    }
    @PostMapping("signupUser")
    public String signupUser(@RequestBody User user) {
        return userService.signupUser(user);
    }

    @GetMapping("isUserLoggedIn")
    public String isUserLoggedIn() {
        return userService.isUserLoggedIn();
    }
}


/*
As saving user, fetching user is independent of underlying used authentication method

We will have a userservice, validationService, AuthenticationService, UserRepository


userservice is a class that acts as business logic layer
userService will validate input user data both at the time of sign up and login (as there is no complex validation as for now, userservice can do the validation)
once successful user service will talk to authService for doing authentication
at sign up if authentication is successful (if password hashing was successful), userService will talk to userRepo to save the user and will talk to either sessionManager or tokenManager for creating it
at login user will be fetch first and both request password and db password info will be sent to authentication service, and then userservice will talk to either sessionManager or tokenManager for creating iteither sessionManager or tokenManager for creating it

Signup Flow:

UserService validates input data

UserService checks if user exists (via UserRepository)

UserService hashes password (via AuthenticationService)

UserService saves user (via UserRepository)

UserService creates session/token (via SessionManager)

Login Flow:

UserService validates input data

UserService fetches user (via UserRepository)

UserService verifies password (via AuthenticationService)

UserService creates session/token (via SessionManager)

Learning train wreck, bidirectional communication

authenticationService will tell userService of auth or validation status and then userservice will talk to tokenService or sessionService to create jwt token or redis-session
authenticationService hold the userRepo interface once userService tell it, it can create or fetch user from db,
authService has two implementations: one is for jwt(creating, validating, expiring, renewing), second is for redis-session(creating, loggingout, expiring)
userService will apply validation rules on user data and once successful it will talk to userRepository to save user data in database or fetch user info at the time of login
userRepo will have two implementations: one is for sql, another is for no sql

 */