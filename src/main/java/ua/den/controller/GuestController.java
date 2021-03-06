package ua.den.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.dto.UserRegistrationDTO;
import ua.den.model.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class GuestController {
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String handleEmptyPath() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal principal) {
        if (principal != null && principal.getName() != null) {
            return "redirect:/home";
        }

        return "guest/login";
    }

    @GetMapping("sign_up")
    public ModelAndView getSignUpPage() {
        ModelAndView modelAndView = new ModelAndView("guest/register_page");

        modelAndView.addObject("userData", new UserRegistrationDTO());

        return modelAndView;
    }

    @PostMapping("sign_up")
    public String signUpUser(@ModelAttribute(name = "userData") @Valid UserRegistrationDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "guest/register_page";
        } else {
            userService.saveNewUser(userDTO);
            return "redirect:/login?sign_up=true";
        }
    }
}