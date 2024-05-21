package com.gk.service;

import com.gk.dto.PaymentDetails;
import com.gk.model.BillingAddress;
import com.gk.model.TransactionDetails;
import com.gk.repo.BillingRepository;
import com.gk.repo.TransactionRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;
    private final TransactionRepository transactionRepository;
    private final BillingRepository billingRepository;

    public Payment createPayment(PaymentDetails paymentDetails, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        //convert INR to USD Dollar below
        double exchangeRate = 0.011975;
        Double total = paymentDetails.getTotal() * exchangeRate;
        amount.setCurrency("USD");
        amount.setTotal(String.format(Locale.forLanguageTag("USD"), "%.2f", total));
        // 9.99$ - 9,99€
        Transaction transaction = new Transaction();
        transaction.setDescription(paymentDetails.getAdditionalInfo());
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

    public void billingAddress(PaymentDetails paymentDetails) {
        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setAddress1(paymentDetails.getAddress1());
        billingAddress.setAddress2(paymentDetails.getAddress2());
        billingAddress.setCity(paymentDetails.getCity());
        billingAddress.setState(paymentDetails.getState());
        billingAddress.setPostalCode(paymentDetails.getPostalCode());
        billingAddress.setCountry("INDIA");
        billingRepository.save(billingAddress);
    }

    public void transactionDetails(PaymentDetails paymentDetails) {
        TransactionDetails details = new TransactionDetails();
        details.setAmount(paymentDetails.getTotal());
        details.setSign("+");
        details.setMode("Online");
        details.setLocalDate(LocalDate.now());
        details.setStatus("Done");
        transactionRepository.save(details);
    }
}