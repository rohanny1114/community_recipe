package com.example.board.controller;

import com.example.board.dto.SigninInfo;
import com.example.board.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Component that gets HTTP request from the browser and responds
 *
 * @author Rohan Kim
 */
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final PostService boardService;

    /**
     * Forward the board template.
     * ClassPath: resources/templates/
     * Spring inputs automatically httpsession (session) and model (parse Thymeleaf template the values)
     *
     * @return list the name of the template
     */
    @GetMapping("/")
    public String list(HttpSession session, Model model) {
        SigninInfo signinInfo = (SigninInfo)session.getAttribute("signinInfo");
        model.addAttribute("signinInfo", signinInfo);
        return "list";
    }

    @GetMapping("/post")
    public String post(@RequestParam("id") int id){
        System.out.println("ID: "+ id);

        return "post";
    }

    @GetMapping("/write")
    public String writeForm(HttpSession session, Model model) {
        //only for signed user, read sign in information in the session
        // or sent to the home
        SigninInfo signinInfo = (SigninInfo)session.getAttribute("signinInfo");
        if (signinInfo == null) { // redirect to signin if the session has no user info
            return "redirect:/signin";
        }
        model.addAttribute("signinInfo", signinInfo);

        return "write";
    }

    @PostMapping("/recipeReg")
    public String postReg(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            HttpSession session) {
        // TEST INPUT VALUES
        System.out.println("title : " + title);
        System.out.println("content : " + content);

        // only the signed user can write
        // check user information on the session
        // if not signed in, parse to the home page.

        SigninInfo signinInfo = (SigninInfo)session.getAttribute("signinInfo");
        if (signinInfo == null) { // redirect to signin if the session has no user info
            return "redirect:/signin";
        }
        boardService.addPost(signinInfo.getUserId(), title, content);

        return "redirect:/";
    }
}
