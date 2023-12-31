package com.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info/")
public class InformationController {


    @GetMapping("/getproducts/")
    public String getProducts() {
        return "List of products";
    }

    @GetMapping("/getstats/")
    public String getStats() {
        return "Statistics";
    }
}
