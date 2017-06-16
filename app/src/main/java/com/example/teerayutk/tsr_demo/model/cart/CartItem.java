package com.example.teerayutk.tsr_demo.model.cart;

import com.example.teerayutk.tsr_demo.model.catalog.Product;

public class CartItem {
    private Product product;
    private int quantity;

    /*public CartItem(ProductItem product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }*/

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
