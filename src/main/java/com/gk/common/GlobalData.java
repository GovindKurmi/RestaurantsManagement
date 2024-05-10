package com.gk.common;

import com.gk.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static final List<Product> cart;
    static {
        cart = new ArrayList<>();
    }
}
