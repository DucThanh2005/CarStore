package com.example.carstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHistoryController {

    @GetMapping("/user/history")
    public String userHistory() {
        return "user-history";
    }
}