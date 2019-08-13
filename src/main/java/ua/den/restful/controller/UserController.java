package ua.den.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.restful.model.dto.UserPasswordDto;
import ua.den.restful.model.dto.UserDataDto;
import ua.den.restful.model.entity.User;
import ua.den.restful.model.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("settings")
    public ModelAndView getUserSettings(final Principal principal) {
        User user = userService.getUserByLogin(principal.getName());

        ModelAndView modelAndView = new ModelAndView("user/settings");

        modelAndView.addObject("userData", initializeUserDataDto(user));
        modelAndView.addObject("userPassword", initializeUserPasswordDto());

        return modelAndView;
    }

    @PostMapping("settings")
    public ModelAndView updateUserData(@ModelAttribute("userPassword") final UserPasswordDto userPassword,
                                       final Principal principal,
                                       @ModelAttribute("userData") @Valid final UserDataDto userData,
                                       final BindingResult result) {
        if (result.hasErrors()) {
            userData.setBirthDate(userService.getUserByLogin(principal.getName()).getBirthDate().toLocalDateTime().toLocalDate());
            return new ModelAndView("user/settings");
        } else {
            User userInDB = userService.getUserByLogin(principal.getName());

            userInDB.setName(userData.getName());
            userInDB.setSurname(userData.getSurname());

            userService.updateUserData(userInDB);

            return new ModelAndView("redirect:/user/settings?updated=true");
        }
    }

    @PostMapping("settings/sensitive")
    public ModelAndView updateUserPassword(final Principal principal,
                                           @ModelAttribute("userPassword") @Valid final UserPasswordDto user,
                                           final BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("user/settings");

            modelAndView.addObject("userData", initializeUserDataDto(userService.getUserByLogin(principal.getName())));

            return modelAndView;
        } else {
            User userInDB = userService.getUserByLogin(principal.getName());

            userInDB.setPassword(encodePassword(user.getNewPassword()));

            userService.updateUserData(userInDB);

            return new ModelAndView("redirect:/user/settings?updated_pass=true");
        }
    }

    private UserPasswordDto initializeUserPasswordDto() {
        return new UserPasswordDto();
    }

    private UserDataDto initializeUserDataDto(User user) {
        UserDataDto userDataDto = new UserDataDto();

        userDataDto.setName(user.getName());
        userDataDto.setSurname(user.getSurname());
        userDataDto.setBirthDate(user.getBirthDate().toLocalDateTime().toLocalDate());

        return userDataDto;
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder(11).encode(password);
    }
}
