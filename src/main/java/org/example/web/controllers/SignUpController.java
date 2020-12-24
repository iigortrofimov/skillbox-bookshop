package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.SignUpService;
import org.example.web.dto.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final Logger logger = Logger.getLogger(SignUpController.class);
    private final SignUpService signUpService;

    @Autowired
    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping
    public String signUp(Model model) {
        logger.info("GET /signup returns signup_page.html");
        model.addAttribute("SignUpForm", new SignUpForm());
        return "signup_page.html";
    }

    @PostMapping("/register")
    public String register(SignUpForm signUpForm) {
        if (StringUtils.hasText(signUpForm.getUsername()) && StringUtils.hasText(signUpForm.getPassword())) {
            signUpService.saveNewUser(signUpForm);
            return "redirect:/books/shelf";
        }
        return "redirect:/signup";
    }

}
