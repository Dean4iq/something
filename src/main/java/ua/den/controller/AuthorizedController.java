package ua.den.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getSupportPage() {
        return "authorized/support";
    }

    @PostMapping(value = "support-output", produces = "application/json")
    @ResponseBody()
    public Map<String, String> manageSupportMessage(@RequestParam("name_inp") @NotBlank String name,
                                                    @RequestParam("email_inp") @NotBlank String email,
                                                    @RequestParam("subject_inp") @NotBlank String subject,
                                                    @RequestParam("text_inp") @NotEmpty String text,
                                                    BindingResult bindingResult) {
        Map<String, String> message = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError e : errors) {
                message.put("@" + e.getField().toUpperCase(), e.getDefaultMessage());
            }

            message.put("status", "input_errors");
            return message;
        }

        message.put("status", "success");
        return message;
    }
}
