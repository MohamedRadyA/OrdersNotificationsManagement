package com.app.rest;

import com.app.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info/")
public class InformationController {

    private InformationService informationService;

    @Autowired
    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/getproducts/")
    public String getProducts() {
        return "List of products";
    }

    @GetMapping("/getstats/")
    public String getStats() {
        return "Statistics";
    }
}
