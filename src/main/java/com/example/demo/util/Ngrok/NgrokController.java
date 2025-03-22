package com.example.demo.util.Ngrok;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NgrokController {
    private final NgrokService ngrokService;

    public NgrokController(NgrokService ngrokService) {
        this.ngrokService = ngrokService;
    }

    @GetMapping("/ngrok-url")
    public String getNgrokUrl() {
        return ngrokService.getNgrokUrl();
    }
}