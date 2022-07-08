package dev.ritam.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authorization/api")
public class SwaggerController {
    @GetMapping
    public String docs() {
        return "redirect:/swagger-ui/index.html";
    }
}
