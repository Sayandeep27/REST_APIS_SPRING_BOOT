package net.javaguides.springboot_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    // Home Page
    @GetMapping("/")
    public String home() {
        return "index";   // loads index.html
    }

    // Welcome Page
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome"; // loads welcome.html
    }

    // Hello Page
    @GetMapping("/hello")
    public String hello() {
        return "hello";   // loads hello.html
    }
}
