package com.gk.dto;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUnit {
    private List<Item> items;
    private Amount amount;
}
