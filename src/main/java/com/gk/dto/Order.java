package com.gk.dto;

import com.paypal.api.payments.RedirectUrls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String intent;
    private List<PurchaseUnit> purchase_units;
    private RedirectUrls application_context;
}
