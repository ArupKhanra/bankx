package arup.Xiaomi.bankx.service;

import arup.Xiaomi.bankx.emailService.EmailService;
import arup.Xiaomi.bankx.entity.Customer;
import arup.Xiaomi.bankx.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService; // Inject EmailService

    @Transactional
    public Customer onboardCustomer(String firstName, String lastName, String email, String phoneNumber) {

        // Create and save the customer
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        // Send welcome email
        String subject = "Welcome to Xiaomi Bank!";
        String body = "Dear "
                + firstName +
                ",\n\nThank you for onboarding with Xiaomi Bank.\n\nBest regards,\nXiaomi Bank Team";
        emailService.sendEmail(email, subject, body);

        return savedCustomer;
    }
}