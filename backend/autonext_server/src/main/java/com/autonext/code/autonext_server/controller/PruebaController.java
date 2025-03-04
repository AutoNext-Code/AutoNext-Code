package com.autonext.code.autonext_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class PruebaController {

    @GetMapping
    public String HolaMundo() {
        return "Hola mundo";
    }
}
