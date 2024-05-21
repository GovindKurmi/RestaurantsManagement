package com.gk.controller;

import com.gk.common.GlobalData;
import com.gk.dto.PaymentDetails;
import com.gk.model.Category;
import com.gk.model.Product;
import com.gk.service.CategoryService;
import com.gk.service.PaypalService;
import com.gk.service.ProductService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static com.gk.common.Constant.CANCEL_URL;
import static com.gk.common.Constant.SUCCESS_URL;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final PaypalService payPalService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String sopping(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String soppingByCategory(Model model, @PathVariable("id") Long id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.findProductById(id));
        return "viewProduct";
    }

    @GetMapping("categories/{id}")
    public String getCategories(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id).orElseThrow();
        List<Product> products = productService.findProductByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategory());
        return "shop";
    }

    @GetMapping("cart/checkout")
    public String checkout(Model model) {
        model.addAttribute("payment", new PaymentDetails());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }

    @PostMapping("/payNow")
    public RedirectView payNow(@ModelAttribute("payment") PaymentDetails paymentDetails, Model model) throws PayPalRESTException {
        double totalAmount = GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        log.info("{}", paymentDetails);
        paymentDetails.setTotal(totalAmount);
        try {
            Payment payments = payPalService.createPayment(paymentDetails, CANCEL_URL, SUCCESS_URL);
            for (Links links : payments.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    payPalService.transactionDetails(paymentDetails);
                    payPalService.billingAddress(paymentDetails);
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("payment/cancel")
    public String PaymentCancel() {
        return "cancelPage";
    }

    @GetMapping("/payment/error")
    public String PaymentError() {
        return "paymentFailed";
    }

    @GetMapping("/order/placed")
    public String paymentSuccess(Model model, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                model.addAttribute("payment", payment);
                model.addAttribute("payer", payment.getPayer());
                model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
                model.addAttribute("cartCount", GlobalData.cart.size());
                return "orderPlaced";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return "orderPlaced";
    }


}
