package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class controls the user related actions.
 *
 * @Author Rohan Kim
 */
@Controller
public class UserController {
    /**
     * Forward the join form templete.
     *
     * @return join the page for a new user registration form
     */
    @GetMapping("/join")
    public String join() {
        return "join";
    }

    /**
     * Request server to save input user information to the database
     *
     * @param name is the name of the new user
     * @param email is the email of the new user to be used as id
     * @param password is the password of the new user
     * @return redirect:/welcome if registration success
     */
    @PostMapping("/userReg")
    public String userReg(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        // TEST INPUT VALUES
        System.out.println("name : " + name);
        System.out.println("email : " + email);
        System.out.println("password : " + password);


        return "redirect:/welcome"; //  브라우저에게 자동으로 http://localhost:8080/welcome 으로 이동
    }

    /**
     *
     * @return welcome the page to show that the registration has successfully done
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * Sign in to the board
     *
     * @return signin the page for signing in form
     */
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    /**
     * Check if the input user information exist in the database,
     * save the user information to the session if it matches with exiting data,
     * and return failure messages if it does match.
     *
     * @param email is the email of the exist user to be used as id
     * @param password is the password of the exist user
     * @return redirect to the home page if the input user information matched
     */
    @PostMapping("/userChk")
    public String userChk(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        // TEST INPUT VALUES
        System.out.println("email : " + email);
        System.out.println("password : " + password);

        return "redirect:/";
    }

    /**
     * Sign out from the board and remove user information from the session
     *
     * @return redirect to home page
     */
    @GetMapping("/signout")
    public String signout() {
        return "redirect:/";
    }
}
