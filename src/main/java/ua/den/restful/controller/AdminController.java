package ua.den.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.restful.model.entity.User;
import ua.den.restful.model.service.UserService;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final Integer ELEMENTS_ON_PAGE = 10;

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ModelAndView getUsersData(@Nullable final Integer page) {
        ModelAndView modelAndView = new ModelAndView("admin/users");

        int pageNumber = (page != null) ? page : 0;

        modelAndView.addObject("userList", userService.getAllUsers(pageNumber, ELEMENTS_ON_PAGE));

        return modelAndView;
    }

    @GetMapping("users/{login}")
    public ModelAndView getUser(@PathVariable("login") final User user) {
        ModelAndView modelAndView = new ModelAndView("admin/specified_user");

        modelAndView.addObject("userData", user);

        return modelAndView;
    }

    @PutMapping("users")
    public ModelAndView updateUsersData(@ModelAttribute("userList") final List<User> userList) {
        ModelAndView modelAndView = new ModelAndView("admin/users");

        List<User> updatedUsers = userService.updateAllUserData(userList);

        modelAndView.addObject("userList", updatedUsers);

        return modelAndView;
    }

    @DeleteMapping("users")
    public ModelAndView deleteAllUsers() {
        ModelAndView modelAndView = new ModelAndView("admin/users");

        userService.deleteAllUsers();

        return modelAndView;
    }
}
