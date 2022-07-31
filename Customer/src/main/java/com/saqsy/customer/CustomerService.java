package com.saqsy.customer;

import com.saqsy.amqp.RabbitMQMessageProducer;
import com.saqsy.client.fraud.FraudCheckResponse;
import com.saqsy.client.fraud.FraudClient;
import com.saqsy.client.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    private final FraudClient fraudClient;


    public void registerCustomer(CustomerRegistrationRequest customerRequest) {

        Customer customer = Customer.builder().firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail()).build();
        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.getIsFraudster()) {
            throw new IllegalStateException("fraudster");
        }
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                    FraudCheckResponse.class,
//                    customer.getId()
//        );

        // todo: send notification


        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Amigoscode...",
                        customer.getFirstName()));

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
