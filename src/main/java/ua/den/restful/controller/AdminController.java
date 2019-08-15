package ua.den.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.restful.model.entity.User;
import ua.den.restful.model.enums.AuthorityType;
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

    @PostMapping("users/{login}/disable")
    public ModelAndView disableUserAccount(@PathVariable("login") final User user) {
        if (user.getAuthority().getRole().equals(AuthorityType.ROLE_USER)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/admin/users/" + user.getLogin() + "?updated=true");

            user.setEnabled(false);
            userService.updateUserData(user);

            return modelAndView;
        } else {
            return new ModelAndView("redirect:/admin/users/" + user.getLogin() + "?unacceptable=true");
        }
    }

    @PostMapping("users/{login}/enable")
    public ModelAndView enableUserAccount(@PathVariable("login") final User user) {
        if (user.getAuthority().getRole().equals(AuthorityType.ROLE_USER)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/admin/users/" + user.getLogin() + "?updated=true");

            user.setEnabled(true);
            userService.updateUserData(user);

            return modelAndView;
        } else {
            return new ModelAndView("redirect:/admin/users/" + user.getLogin() + "?unacceptable=true");
        }
    }

    @PutMapping("users")
    public ModelAndView updateUsersData(@ModelAttribute("userList") final List<User> userList) {
        ModelAndView modelAndView = new ModelAndView("admin/users");

        List<User> updatedUsers = userService.updateAllUserData(userList);

        modelAndView.addObject("userList", updatedUsers);

        return modelAndView;
    }

    @PostMapping("users/delete")
    public ModelAndView deleteAllUsers() {
        ModelAndView modelAndView = new ModelAndView("admin/users?all_deleted=true");

        userService.deleteAllUsersButAdmin();

        return modelAndView;
    }

    @PostMapping("users/{login}/delete")
    public ModelAndView deleteUserAccount(@PathVariable("login") final User user) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users?acc_deleted=true");

        userService.deleteUser(user);
        modelAndView.addObject("userAccount", user.getLogin());

        return modelAndView;
    }
}
