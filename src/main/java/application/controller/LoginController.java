package application.controller;

import application.dto.LoginRequest;
import application.model.Movie;
import jakarta.servlet.http.HttpSession;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import application.repository.UserRepository;
import application.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") LoginRequest loginRequest, Model model, HttpSession session) {
         Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                // Đăng nhập thành công
                session.setAttribute("loggedInUser", user);
                return "redirect:/home";
            }
        }

        // Đăng nhập thất bại
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        List<Movie> movies = movieRepository.findAll();
        System.out.print(movies);
        model.addAttribute("movies", movies);

        model.addAttribute("username", user.getUsername());
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setId(null);
            userRepository.save(user);
            model.addAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập.");
            model.addAttribute("user", new User()); // reset form
        } else {
            model.addAttribute("error", "Tên người dùng đã tồn tại.");
        }
        return "register";
    }
}
