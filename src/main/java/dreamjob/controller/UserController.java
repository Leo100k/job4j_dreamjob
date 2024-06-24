package dreamjob.controller;

/*
public class UserController {

   @RequestMapping("/users")
    register: GET getRegistationPage()
    POST register();
}

 */

import dreamjob.model.User;
import dreamjob.service.UserService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@ThreadSafe
@Controller
@RequestMapping("/users") /* Работать с пользователями будем по URI /users/** */
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.save(user);
        System.out.println("USER ПУСТОЙ ? " + savedUser.isEmpty());
        if (savedUser.isEmpty()) {
            System.out.println(" ЗАШЕЛ в IF в котроллере");

            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "errors/404";
        }
        System.out.println(" НЕ зашёл в IF");
        return "redirect:/vacancies";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model) {
        var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        return "redirect:/vacancies";
    }
}