package com.saqsy.notification;

import lombok.Data;

@Data
public class NotificationRequest {
    Integer toCustomerId;
    String toCustomerName;
    String message;
}
