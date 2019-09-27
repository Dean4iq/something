package ua.den.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.dto.NewsDto;
import ua.den.model.dto.NewsInputDataDto;
import ua.den.model.dto.UserApplySupportDto;
import ua.den.model.service.NewsXmlConverterService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.security.Principal;
import java.util.*;

@Controller
public class AuthorizedController {
    @Autowired
    private NewsXmlConverterService newsXmlConverterService;

    @RequestMapping("login_success")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public ModelAndView getHomePage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/authorized/home");

        modelAndView.addObject("newsLines", getListOfNews(LocaleContextHolder.getLocale(), new Date()));

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> (auth.getAuthority().equals("ROLE_ADMIN")))) {
            modelAndView.addObject("newsData", new NewsInputDataDto());
        }

        return modelAndView;
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
        List<NewsDto> newsList = newsXmlConverterService.getNewsFromXmlAndConvertToDto(locale.getLanguage(), userDate);
        return newsXmlConverterService.sortNewsByPublishDate(newsList);
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
