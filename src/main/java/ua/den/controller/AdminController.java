package ua.den.controller;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.den.model.entity.User;
import ua.den.model.enums.AuthorityType;
import ua.den.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {
    public static final String REDIRECT_ADMIN_USERS = "redirect:/admin/users/";
    private static final Integer ELEMENTS_ON_PAGE = 10;

    @Autowired
    private ApplicationContext context;
    @Autowired
    private BeanFactory factory;
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
            ModelAndView modelAndView = new ModelAndView(REDIRECT_ADMIN_USERS + user.getLogin() + "?updated=true");

            user.setEnabled(false);
            userService.updateUserData(user);

            return modelAndView;
        } else {
            return new ModelAndView(REDIRECT_ADMIN_USERS + user.getLogin() + "?unacceptable=true");
        }
    }

    @PostMapping("users/{login}/enable")
    public ModelAndView enableUserAccount(@PathVariable("login") final User user) {
        if (user.getAuthority().getRole().equals(AuthorityType.ROLE_USER)) {
            ModelAndView modelAndView = new ModelAndView(REDIRECT_ADMIN_USERS + user.getLogin() + "?updated=true");

            user.setEnabled(true);
            userService.updateUserData(user);

            return modelAndView;
        } else {
            return new ModelAndView(REDIRECT_ADMIN_USERS + user.getLogin() + "?unacceptable=true");
        }
    }

    @PutMapping("users")
    public ModelAndView updateUsersData(@ModelAttribute("userList") final List<User> userList) {
        ModelAndView modelAndView = new ModelAndView("admin/users");

        List<User> updatedUsers = userService.updateAllUserData(userList);

        modelAndView.addObject("userList", updatedUsers);

        return modelAndView;
    }

    @GetMapping("controlBeans")
    public ModelAndView getControlBeansPage() {
        ModelAndView modelAndView = new ModelAndView("admin/controlBeans");

        List<String> listOfBeans = getBeanNamesSet().stream()
                .map(elem -> {
                    int pointIndex = elem.indexOf('.');
                    if (pointIndex != -1) {
                        return elem.substring(0, elem.indexOf('.'));
                    } else {
                        return elem;
                    }
                })
                .collect(Collectors.toSet()).stream()
                .sorted()
                .collect(Collectors.toList());

        modelAndView.addObject("beansList", listOfBeans);

        return modelAndView;
    }

    @GetMapping("controlBeans/**")
    public ModelAndView getBeanInfo(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin/beanPage");
        String beanBaseName = request.getRequestURI().split("controlBeans/")[1];

        modelAndView.addObject("basePath", beanBaseName);

        beanBaseName = beanBaseName.replace('/', '.');

        if (factory.containsBean(beanBaseName)) {
            modelAndView.addObject("beanInfo", factory.getBean(beanBaseName));
        } else {
            Set<String> beanFullPathNames = getBeanNamesSet();
            String[] beansNames = beanFullPathNames.toArray(new String[0]);
            String[] pathSplitString = beanBaseName.split("\\.");

            modelAndView.addObject("beansListNames", beansNames);

            List<String> listOfBeans = Arrays
                    .stream(beansNames)
                    .filter(elem -> {
                        String[] path = Arrays.copyOfRange(elem.split("\\."), 0, pathSplitString.length);
                        return Arrays.equals(pathSplitString, path);
                    })
                    .map(elem -> {
                        int pointIndex = elem.indexOf('.');
                        if (pointIndex == -1) {
                            return elem;
                        } else {
                            String result = elem.substring(ordinalIndexOf(elem, ".", pathSplitString.length) + 1);
                            if (result.indexOf('.') != -1) {
                                result = result.substring(0, result.indexOf('.'));
                            }

                            return result;
                        }
                    })
                    .collect(Collectors.toSet()).stream()
                    .sorted()
                    .collect(Collectors.toList());

            modelAndView.addObject("beansList", listOfBeans);
        }

        return modelAndView;
    }


    @PostMapping("users/updateStatus")
    public String updateAllUsersStatus(@RequestParam("account_status") boolean status) {
        userService.updateUsersAccountStatus(status);

        return "redirect:/admin/users?all_updated=true";
    }

    @PostMapping("users/delete")
    public ModelAndView deleteAllUsers() {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users?all_deleted=true");

        userService.deleteAllUsersButAdmin();

        return modelAndView;
    }

    @PostMapping("users/{login}/delete")
    public ModelAndView deleteUserAccount(@PathVariable("login") final User user) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users?acc_deleted=true");

        modelAndView.addObject("userAccount", user.getLogin());
        userService.deleteUser(user);

        return modelAndView;
    }

    private Set<String> getBeanNamesSet() {
        String[] beansNames = context.getBeanDefinitionNames();

        return Arrays.stream(beansNames)
                .collect(Collectors.toSet());
    }

    private int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);

        while (--n > 0 && pos != -1) {
            pos = str.indexOf(substr, pos + 1);
        }

        return pos;
    }
}
