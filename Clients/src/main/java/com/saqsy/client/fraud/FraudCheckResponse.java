package com.saqsy.client.fraud;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class FraudCheckResponse {

    Boolean isFraudster;

}
