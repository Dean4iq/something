package ua.den.restful.handlers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final String LOGIN_URI = "/login";

    private Set<String> acceptableURI;

    public CustomAccessDeniedHandler() {
        acceptableURI = new HashSet<>();

        acceptableURI.add("/login");
        acceptableURI.add("/sign_up");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            if (acceptableURI.contains(request.getRequestURI())) {
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/accessDenied");
            }
        } else {
            response.sendRedirect(request.getContextPath() + LOGIN_URI);
        }
    }
}
