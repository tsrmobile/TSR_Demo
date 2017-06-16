package com.example.teerayutk.tsr_demo.framgment.catalog;

import com.example.teerayutk.tsr_demo.model.catalog.Product;

import java.util.List;

/**
 * Created by teerayut.k on 6/6/2560.
 */

public interface CatalogInterface {

    interface viewInterface{
        void onLoad();
        void onSuccess();
        void onFail(String fail);
        void setProduct(List<Product> productList);
    }

    interface presentInterface{
        void loadProduct();
    }
}
