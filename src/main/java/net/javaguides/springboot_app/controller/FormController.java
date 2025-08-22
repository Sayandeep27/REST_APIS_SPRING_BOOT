package net.javaguides.springboot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import net.javaguides.springboot_app.model.UserInput;
import net.javaguides.springboot_app.repository.UserInputRepository;
import net.javaguides.springboot_app.util.EncryptionUtil;

@Controller
public class FormController {

    @Autowired
    private UserInputRepository userInputRepository;

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("userInput", new UserInput());
        return "form";
    }

    @PostMapping("/submitForm")
    public String submitForm(@ModelAttribute UserInput userInput) {
        // Save normally (no encryption)
        userInputRepository.save(userInput);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        var users = userInputRepository.findAll();

        // Only mask before showing
        for (UserInput user : users) {
            user.setEmail(EncryptionUtil.maskEmail(user.getEmail()));
            user.setPhoneNumber(EncryptionUtil.maskPhone(user.getPhoneNumber()));
        }

        model.addAttribute("users", users);
        return "users";
    }
}
