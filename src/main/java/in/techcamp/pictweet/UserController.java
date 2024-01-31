package in.techcamp.pictweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registerForm")
    public String register(@ModelAttribute("user")UserEntity userEntity){
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user")UserEntity userEntity,Model model){
        try {
            userService.registerNewUser(userEntity);
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "register";
        }
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "名前かパスワードが間違っています。");
        }
        return "login";
    }
}