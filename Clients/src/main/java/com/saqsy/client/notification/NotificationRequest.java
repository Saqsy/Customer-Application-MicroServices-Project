package com.saqsy.client.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequest {
    Integer toCustomerId;
    String toCustomerName;
    String message;
}
