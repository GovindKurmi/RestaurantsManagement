package com.gk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDetails {

    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String email;
    private String state;
    private String country;
    private String postalCode;
    private String additionalInfo;
    private Double total;
}
