package com.saqsy.customer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;


    public void registerCustomer(CustomerRegistrationRequest customerRequest) {

        Customer customer = Customer.builder().firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail()).build();
        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                    FraudCheckResponse.class,
                    customer.getId()
        );

        if (fraudCheckResponse.getIsFraudster()){
            throw new IllegalStateException("fraudster");
        }
        // todo: send notification
    }
}
