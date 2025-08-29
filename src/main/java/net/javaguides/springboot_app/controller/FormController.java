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
        // Encrypt before saving
        userInput.setEmail(EncryptionUtil.encrypt(userInput.getEmail()));
        userInput.setPhoneNumber(EncryptionUtil.encrypt(userInput.getPhoneNumber()));
        userInputRepository.save(userInput);
        return "redirect:/users";
    }

    // Normal User View (Masked)
    @GetMapping("/users")
    public String showUsers(Model model) {
        var users = userInputRepository.findAll();
        for (UserInput user : users) {
            String decryptedEmail = EncryptionUtil.decrypt(user.getEmail());
            String decryptedPhone = EncryptionUtil.decrypt(user.getPhoneNumber());

            user.setEmail(EncryptionUtil.maskEmail(decryptedEmail));
            user.setPhoneNumber(EncryptionUtil.maskPhone(decryptedPhone));
        }
        model.addAttribute("users", users);
        return "users";
    }

    // Admin View (Decrypted Data)
    @GetMapping("/admin/users")
    public String showAdminUsers(Model model) {
        var users = userInputRepository.findAll();
        for (UserInput user : users) {
            user.setEmail(EncryptionUtil.decrypt(user.getEmail()));
            user.setPhoneNumber(EncryptionUtil.decrypt(user.getPhoneNumber()));
        }
        model.addAttribute("users", users);
        return "admin-users";
    }
}
