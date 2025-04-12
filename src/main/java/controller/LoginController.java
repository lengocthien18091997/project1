package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import model.User;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model) {    	
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String loginSublit(@ModelAttribute User user, Model model) {    	
    	if ("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
            model.addAttribute("username", user.getUsername());
            return "home";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "login";
        }    	
    }
}
