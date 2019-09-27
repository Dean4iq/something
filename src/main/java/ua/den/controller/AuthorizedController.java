package ua.den.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.dto.NewsDto;
import ua.den.model.dto.UserApplySupportDto;
import ua.den.model.service.NewsXmlConverterService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Controller
public class AuthorizedController {
    @RequestMapping("login_success")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("/authorized/home");

        modelAndView.addObject("newsLines", getListOfNews(LocaleContextHolder.getLocale(), new Date()));

        return modelAndView;
    }

    @PostMapping(value = "home-news", produces = "application/json")
    @ResponseBody
    public List<NewsDto> getNewsForHomePage(@RequestParam("userDateTime") Date userDate) {
        return getListOfNews(LocaleContextHolder.getLocale(), userDate);
    }

    @GetMapping("accessDenied")
    public String getAccessDeniedPage(HttpServletResponse response) {
        return "authorized/access_denied";
    }

    @GetMapping("support")
    public ModelAndView getSupportPage() {
        ModelAndView modelAndView = new ModelAndView("authorized/support");

        modelAndView.addObject("subjects", getSubjectsList());

        return modelAndView;
    }

    @RequestMapping(value = "support-output", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> manageSupportMessage(String name, String email, String subject, String text) {
        UserApplySupportDto userApplySupportDto = new UserApplySupportDto();

        userApplySupportDto.setName(name);
        userApplySupportDto.setEmail(email);
        userApplySupportDto.setSubject(subject);
        userApplySupportDto.setText(text);

        return validateFieldsAndGetStatusMap(userApplySupportDto);
    }

    private List<NewsDto> getListOfNews(Locale locale, Date userDate) {
        return new NewsXmlConverterService().getNewsFromXmlAndConvertToDto(locale.getLanguage(), userDate);
    }

    private List<String> getSubjectsList() {
        String[] subjects = new String[]{"support.subject.technical_difficulties", "support.subject.user_report", "support.subject.other"};

        return Arrays.asList(subjects);
    }

    private Map<String, Object> validateFieldsAndGetStatusMap(UserApplySupportDto object) {
        Map<String, Object> messages = new HashMap<>(1);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserApplySupportDto>> violations = validator.validate(object);

        if (violations.size() != 0) {
            messages.put("status", "input_errors");

            String[] fieldsWithErrors = new String[violations.size()];
            int iteration = 0;

            for (ConstraintViolation<UserApplySupportDto> violation : violations) {
                fieldsWithErrors[iteration++] = violation.getPropertyPath().toString() + "-error";
            }

            messages.put("fields", fieldsWithErrors);

            return messages;
        }

        messages.put("status", "success");
        return messages;
    }
}
