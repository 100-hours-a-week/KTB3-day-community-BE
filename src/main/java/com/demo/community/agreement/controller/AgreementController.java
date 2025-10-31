package com.demo.community.agreement.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgreementController {

    @GetMapping("/agreement")
    public String agreement(Model model) {
        model.addAttribute("name", "홍대의");
        model.addAttribute("nextPage", "http://localhost:3000/public/pages/posts/posts.html");
        return "agreement";
    }
}
