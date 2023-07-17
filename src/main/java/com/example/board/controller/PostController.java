package com.example.board.controller;

import com.example.board.dto.Post;
import com.example.board.dto.SigninInfo;
import com.example.board.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Component that gets HTTP request from the browser and responds
 *
 * @author Rohan Kim
 */
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

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

        int page = 1;
        int totalPosts = postService.getTotalPosts();
        List<Post> posts = postService.getPosts(page);
        int totalPage = totalPosts/10;
        if (totalPosts % 10 > 0 ){
            totalPage++;
        }
        int currentPage = page;
        model.addAttribute("posts", posts);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);

        return "list";
    }

    @GetMapping("/post")
    public String post(@RequestParam("postId") int postId, Model model){
        System.out.println("POST ID: "+ postId);

        Post post = postService.getPost(postId);
        model.addAttribute("post", post);

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

    @PostMapping("/regPost")
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
        postService.addPost(signinInfo.getUserId(), title, content);
        return "redirect:/";
    }

    /**
     * This method delete the selected post
     * @param postId Id of selected post
     * @param session
     * @return
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("postId") int postId, HttpSession session){
        SigninInfo signinInfo= (SigninInfo) session.getAttribute("signinInfo");
        // Back to signin form if no login information on the session
        if(signinInfo == null){
            return "redirect:/signin";
        }
        List<String> roles = signinInfo.getRoles();
        if (roles.contains("ROLE_ADMIN")){
            postService.deletePost(postId);
        } else {
            // Verify the writer of the post and the logged-in user
            postService.deletePost(signinInfo.getUserId(), postId);
        }
        return "redirect:/";
    }

    @GetMapping("/updatePost")
    public String updatePost(@RequestParam("postId") int postId, Model model, HttpSession session){
        SigninInfo signinInfo= (SigninInfo) session.getAttribute("signinInfo");
        if(signinInfo == null){
            return "redirect:/signin";
        }
        Post post = postService.getPost(postId, false);
        model.addAttribute("post", post);
        model.addAttribute("signinInfo", signinInfo);
        return "updatePost";
    }
    @GetMapping("/update")
    public String update(@RequestParam("postId")int postId,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                        HttpSession session){
        SigninInfo signinInfo= (SigninInfo) session.getAttribute("signinInfo");
        if(signinInfo == null){
            return "redirect:/signin";
        }
        Post post = postService.getPost(postId, false);
        if(post.getUserId() !=signinInfo.getUserId()){
            return "redirect:/post?postId=" +postId;
        }
        postService.updatePost(postId, title, content);

        return "redirect:/post?postId="+postId;
    }
}
