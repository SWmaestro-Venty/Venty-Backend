package com.swm.ventybackend.dynamoDb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/add/customer")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerRepository.saveCustomer(customer);
    }

    @GetMapping("/get/customer/{id}")
    public Customer getCustomerById(@PathVariable("id") String customerId) {
        return customerRepository.getCustomerById(customerId);
    }

    @DeleteMapping("/delete/customer/{id}")
    public String deleteCustomerById(@PathVariable("id") String customerId) {
        return  customerRepository.deleteCustomerById(customerId);
    }

    @PutMapping("/update/customer/{id}")
    public String updateCustomer(@PathVariable("id") String customerId, @RequestBody Customer customer) {
        return customerRepository.updateCustomer(customerId,customer);
    }
}