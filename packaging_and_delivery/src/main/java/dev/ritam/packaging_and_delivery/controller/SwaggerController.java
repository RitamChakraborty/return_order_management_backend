package dev.ritam.packaging_and_delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/packaging-and-delivery/api")
public class SwaggerController {
    @GetMapping("/docs")
    public String docs() {
        return "redirect:/swagger-ui/";
    }
}
