package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Component that gets HTTP request from the browser and responds
 *
 * @author Rohan Kim
 */
@Controller
public class BoardController {
    /**
     * Forward the board template.
     * ClassPath: resources/templates/
     *
     * @return list the name of the template
     */
    @GetMapping("/")
    public String list() {
        return "list";
    }

    @GetMapping("/post")
    public String post(@RequestParam("id") int id){
        System.out.println("ID: "+ id);

        return "post";
    }

    @GetMapping("/write")
    public String writeForm() {
        //only for signed user, read sign in information in the session
        // or sent to the home
        return "write";
    }

    @PostMapping("/recipeReg")
    public String recipeReg(@RequestParam("title") String title,
                            @RequestParam("content") String content
    ) {
        // TEST INPUT VALUES
        System.out.println("title : " + title);
        System.out.println("content : " + content);

        // only the signed user can write
        // check user information on the session
        // if not signed in, parse to the home page.

        return "redirect:/";
    }
}
