package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Component that gets HTTP request from the browser and responds
 *
 * @Author Rohan Kim
 */
@Controller
public class BoardController {
    /**
     * Forward the borad templete.
     * ClassPath: resources/templates/
     *
     * @return list the name of the templete
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
}
