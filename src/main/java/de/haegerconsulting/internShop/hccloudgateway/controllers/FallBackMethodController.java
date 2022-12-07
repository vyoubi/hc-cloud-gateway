package de.haegerconsulting.internShop.hccloudgateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping
public class FallBackMethodController {

    @GetMapping("EmployeeServiceFallBack")
    public String playerServiceFallBackMethod() {
        return "Player Service is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("departmentServiceFallBack")
    public String nationalityServiceFallBackMethod() {
        return "Department Service is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("productServiceFallBack")
    public String productServiceFallBackMethod() {
        return "Product Service is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("orderServiceFallBack")
    public String orderServiceFallBackMethod() {
        return "Order Service is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("inventoryServiceFallBack")
    public String inventoryServiceFallBackMethod() {
        return "Inventory Service is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("paymentServiceFallBack")
    public String paymentServiceFallBackMethod() {
        return "Payment Service is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("notificationServiceFallBack")
    public String notificationServiceFallBackMethod() {
        return "Send notification is taking longer than expected. " + "Please try again later";
    }

    @GetMapping("/")
    public String index(Principal principal) {
        return principal.getName();
    }
}
