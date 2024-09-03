package com.yigroup.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import com.yigroup.ecsite.entity.Campaign;
// import com.yigroup.ecsite.entity.Product;
// import com.yigroup.ecsite.service.CampaignService;
// import com.yigroup.ecsite.service.ProductService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yigroup.ecsite.entity.Product;
import com.yigroup.ecsite.service.ProductService;

// import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    // private final CampaignService campaignService;
    // private final ProductService productService;

    // @Autowired
    // public HomeController(CampaignService campaignService, ProductService
    // productService) {
    // this.campaignService = campaignService;
    // this.productService = productService;
    // }

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.getAllProducts();
        System.out.println(products.get(0).getImages());

        model.addAttribute("products", products);
        return "index";
    }
}
