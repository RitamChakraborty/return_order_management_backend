package dev.ritam.component_processing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/component-processing/api")
public class SwaggerController {
    @GetMapping("/docs")
    public String docs() {
        return "redirect:/swagger-ui/";
    }
}
