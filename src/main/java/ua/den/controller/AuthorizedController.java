package ua.den.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.dto.NewsDto;
import ua.den.model.dto.NewsInputDataDto;
import ua.den.model.dto.UserApplySupportDto;
import ua.den.model.service.NewsXmlConverterService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Controller
public class AuthorizedController {
    @Autowired
    private NewsXmlConverterService newsXmlConverterService;

    @RequestMapping(path = "login_success", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public ModelAndView getHomePage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/authorized/home");

        OffsetDateTime currentDateTime = OffsetDateTime.now(ZoneOffset.UTC).withNano(0);
        modelAndView.addObject("newsLines", getListOfNews(LocaleContextHolder.getLocale(), currentDateTime));

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> (auth.getAuthority().equals("ROLE_ADMIN")))) {
            modelAndView.addObject("newsData", new NewsInputDataDto());
        }

        return modelAndView;
    }

    @PostMapping("home")
    public String processNewNews(@ModelAttribute("newsData") @Valid NewsInputDataDto newsData,
                                 BindingResult bindingResult) {
        OffsetDateTime currentDateTime = OffsetDateTime.now(ZoneOffset.UTC).withNano(0);

        newsXmlConverterService.addNewNewsDataToXml(newsData, currentDateTime);

        return "redirect:/home";
    }

    @GetMapping("accessDenied")
    public String getAccessDeniedPage(HttpServletResponse response) {
        return "authorized/access_denied";
    }

    @GetMapping("about")
    public String getAboutPage() {
        return "authorized/about";
    }

    @GetMapping("support")
    public ModelAndView getSupportPage() {
        ModelAndView modelAndView = new ModelAndView("authorized/support");

        modelAndView.addObject("subjects", getSubjectsList());

        return modelAndView;
    }

    @PostMapping(value = "support-output", produces = "application/json")
    @ResponseBody
    public Map<String, Object> manageSupportMessage(String name, String email, String subject, String text) {
        UserApplySupportDto userApplySupportDto = new UserApplySupportDto();

        userApplySupportDto.setName(name);
        userApplySupportDto.setEmail(email);
        userApplySupportDto.setSubject(subject);
        userApplySupportDto.setText(text);

        return validateFieldsAndGetStatusMap(userApplySupportDto);
    }

    private List<NewsDto> getListOfNews(Locale locale, OffsetDateTime userDate) {
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

        if (!violations.isEmpty()) {
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
