package com.example.teerayutk.tsr_demo.model.cart;

import com.example.teerayutk.tsr_demo.model.catalog.CatalogModel;

/**
 * Created by teerayut.k on 9/6/2560.
 */

public class CartModel {

    private int quantity;
    private CatalogModel catalog;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CatalogModel getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogModel catalog) {
        this.catalog = catalog;
    }
}
