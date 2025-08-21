package net.javaguides.springboot_app;

import net.javaguides.springboot_app.model.UserInput;
import net.javaguides.springboot_app.repository.UserInputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FormController {

    @Autowired
    private UserInputRepository repository;

    // Show form
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("userInput", new UserInput());
        return "form";  // form.html
    }

    // Handle form submission
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute UserInput userInput, Model model) {
        repository.save(userInput);  // Save to DB
        model.addAttribute("message", "Data saved successfully!");
        return "result";  // result.html
    }

    // Show all users
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "users";  // users.html
    }
}
