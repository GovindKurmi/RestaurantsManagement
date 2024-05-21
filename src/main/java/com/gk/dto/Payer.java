package com.gk.dto;

import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.FundingOption;
import com.paypal.api.payments.PayerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payer {
    private String paymentMethod;
    private String status;
    private String accountType;
    private String accountAge;
    private List<FundingInstrument> fundingInstruments;
    private String externalSelectedFundingInstrumentType;
    private FundingOption relatedFundingOption;
    private PayerInfo payerInfo;
}
