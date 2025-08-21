package net.javaguides.springboot_app.controller;

import net.javaguides.springboot_app.model.UserInput;
import net.javaguides.springboot_app.repository.UserInputRepository;
import net.javaguides.springboot_app.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        // Encrypt email & phone before saving
        userInput.setEmail(EncryptionUtil.encrypt(userInput.getEmail()));
        userInput.setPhoneNumber(EncryptionUtil.encrypt(userInput.getPhoneNumber()));

        userInputRepository.save(userInput);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        var users = userInputRepository.findAll();

        // Decrypt & mask before showing
        for (UserInput user : users) {
            String decryptedEmail = EncryptionUtil.decrypt(user.getEmail());
            String decryptedPhone = EncryptionUtil.decrypt(user.getPhoneNumber());

            user.setEmail(EncryptionUtil.maskEmail(decryptedEmail));
            user.setPhoneNumber(EncryptionUtil.maskPhone(decryptedPhone));
        }

        model.addAttribute("users", users);
        return "users";
    }
}
