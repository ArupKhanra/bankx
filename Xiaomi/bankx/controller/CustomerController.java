package arup.Xiaomi.bankx.controller;

import arup.Xiaomi.bankx.entity.Customer;
import arup.Xiaomi.bankx.request.CustomerRequest;
import arup.Xiaomi.bankx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/onboard")
    public Customer onboardCustomer(@RequestBody CustomerRequest request) {
        return customerService.onboardCustomer(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber());
    }
}