package com.example.teerayutk.tsr_demo.model.catalog;

import com.android.tonyvu.sc.model.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Saleable, Serializable {
    private static final long serialVersionUID = -4073256626483275668L;

    private String productID;
    private String productName;
    //private String productQty;
    private String productDesc;
    private String productThumbs;
    private BigDecimal productPrice;

    public Product() {
        super();
    }

    public Product(String productID, String productName/*, String productQty*/, String productDesc, String productThumbs, BigDecimal productPrice) {
        setProductID(productID);
        setProductName(productName);
        //setProductQty(productQty);
        setProductDesc(productDesc);
        setProductThumbs(productThumbs);
        setProductPrice(productPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Product)) return false;

        return (this.productID == ((Product) o).getProductID());
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = hash * prime + (productID == null ? 0 : productID.hashCode());
        hash = hash * prime + (productName == null ? 0 : productName.hashCode());
       // hash = hash * prime + (productQty == null ? 0 : productQty.hashCode());
        hash = hash * prime + (productThumbs == null ? 0 : productThumbs.hashCode());
        hash = hash * prime + (productPrice == null ? 0 : productPrice.hashCode());

        return hash;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public BigDecimal getPrice() {
        return productPrice;
    }

    @Override
    public String getName() {
        return productName;
    }

    public void setProductName(String productDesc) {
        this.productName = productDesc;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }


    /*public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }*/

    public String getProductThumbs() {
        return productThumbs;
    }

    public void setProductThumbs(String productThumbs) {
        this.productThumbs = productThumbs;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
