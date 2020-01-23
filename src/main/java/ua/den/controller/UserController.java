package ua.den.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.dto.TextInfoDto;
import ua.den.model.dto.TextInputDto;
import ua.den.model.dto.UserDataDto;
import ua.den.model.dto.UserPasswordDto;
import ua.den.model.entity.User;
import ua.den.model.service.TextAnalysisService;
import ua.den.model.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    public static final String USER_SETTINGS = "user/settings";

    @Autowired
    private UserService userService;

    @GetMapping({"text_analysis_ua", "text_analysis_en"})
    public ModelAndView getTextAnalysisForm() {
        ModelAndView modelAndView = new ModelAndView("user/textAnalysis");

        modelAndView.addObject("textDataDto", new TextInputDto());

        return modelAndView;
    }

    @PostMapping(value = "text_analysis_ua", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> analyzeUaText(@RequestBody @Valid final TextInputDto textInputDto,
                                             final BindingResult bindingResult,
                                             final HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Object sessionAnalysisInProgressChecker = session.getAttribute("text_analysis_in_progress");

        if (sessionAnalysisInProgressChecker == null || (sessionAnalysisInProgressChecker instanceof Boolean
                && Boolean.FALSE.equals(sessionAnalysisInProgressChecker))) {
            session.setAttribute("text_analysis_in_progress", Boolean.TRUE);

            if (!bindingResult.hasErrors()) {
                TextAnalysisService analysisService = new TextAnalysisService();
                TextInfoDto textInfoDto = analysisService.analyzeText(textInputDto.getTextData());

                result.put("markedOutput", textInfoDto.getMarkedOutput());
                result.put("usagesOutput", textInfoDto.getUsages());
            } else {
                result.put("error", bindingResult.getSuppressedFields());
            }

            session.setAttribute("text_analysis_in_progress", Boolean.FALSE);
        }

        return result;
    }

    @PostMapping("text_analysis_en")
    public ModelAndView analyzeEnText(@ModelAttribute("textDataDto") @Valid final TextInputDto textInputDto,
                                      final BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("user/textAnalysis");

        if (!bindingResult.hasErrors()) {
            TextAnalysisService analysisService = new TextAnalysisService();
            TextInfoDto textInfoDto = analysisService.analyzeText(textInputDto.getTextData());

            modelAndView.addObject("textInfoOutputDto", textInfoDto);
            modelAndView.addObject("textDataDto", textInputDto);
        }

        return modelAndView;
    }

    @GetMapping("settings")
    public ModelAndView getUserSettings(final Principal principal) {
        User user = userService.getUserByLogin(principal.getName());

        ModelAndView modelAndView = new ModelAndView(USER_SETTINGS);

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
            return new ModelAndView(USER_SETTINGS);
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
            ModelAndView modelAndView = new ModelAndView(USER_SETTINGS);

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
