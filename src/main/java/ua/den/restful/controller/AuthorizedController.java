package ua.den.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizedController {
    @RequestMapping("login_success")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public String getHomePage() {
        return "/authorized/home";
    }

    @GetMapping("accessDenied")
    public String getAccessDeniedPage(HttpServletResponse response) {
        return "authorized/access_denied";
    }
}
