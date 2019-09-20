package ua.den.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.dto.UserApplySupportDto;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @GetMapping("support")
    public ModelAndView getSupportPage() {
        ModelAndView modelAndView = new ModelAndView("authorized/support");

        modelAndView.addObject("userApplySupportDto", new UserApplySupportDto());

        return modelAndView;
    }

    @RequestMapping(value = "support-output", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody()
    public Map<String, String> manageSupportMessage(UserApplySupportDto userApplySupportDto) {
        Map<String, String> message = new HashMap<>(1);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserApplySupportDto>> violations = validator.validate(userApplySupportDto);

        if (violations.size() != 0) {
            for (ConstraintViolation<UserApplySupportDto> violation : violations) {
                message.put(violation.getPropertyPath().toString(), violation.getMessage());
            }

            message.put("status", "input_errors");
            return message;
        }

        message.put("status", "success");
        return message;
    }
}
