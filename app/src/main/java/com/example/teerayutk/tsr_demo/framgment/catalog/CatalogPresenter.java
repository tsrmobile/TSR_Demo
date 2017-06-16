package com.example.teerayutk.tsr_demo.framgment.catalog;

import com.example.teerayutk.tsr_demo.model.catalog.Product;
import com.example.teerayutk.tsr_demo.model.catalog.CatalogModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by teerayut.k on 6/6/2560.
 */

public class CatalogPresenter implements CatalogInterface.presentInterface {

    private Product product1, product2, product3;
    private List<Product> productList;
    private List<CatalogModel> catalogModelList = new ArrayList<CatalogModel>();
    private CatalogInterface.viewInterface view;
    public CatalogPresenter(CatalogInterface.viewInterface view) {
        this.view = view;
    }

    @Override
    public void loadProduct() {
        view.onLoad();
        productList = new ArrayList<Product>();
        product1 = new Product(
                "1",
                "เครื่องกรองน้ำ SAFE ROMA Plus",
                "วงจรควบคุมทำงานอัตโนมัติ ปรับการทำงานของเครื่องให้เหมาะสมกับแรงดันน้ำที่เข้าสู่เครื่อง เพื่อการใช้งานอย่างมั่นใจ",
                "http://www.safealkaline.com/media/catalog/product/cache/1/small_image/280x/9df78eab33525d08d6e5fb8d27136e95/r/o/roma-plus_2.jpg",
                BigDecimal.valueOf(Integer.parseInt("3999"))
        );
        productList.add(product1);

        product2 = new Product(
                "2",
                "เหยือกกรองน้ำ รุ่น Ecomize",
                "ใช้งานง่ายสะดวก ทรงประสิทธิภาพด้วยไส้กรองเทคโนโลยีอัลคาไลน์ เสริมแร่ธาตุ ปรับสภาพน้ำให้เป็นน้ำด่างได้ เหมาะกับใช้ที่บ้านและที่ทำงาน",
                "http://www.safealkaline.com/media/catalog/product/cache/1/small_image/280x/9df78eab33525d08d6e5fb8d27136e95/e/c/ecomize2free.jpg",
                BigDecimal.valueOf(Integer.parseInt("2390"))
        );
        productList.add(product2);

        product3 = new Product(
                "3",
                "เครื่องกรองน้ำ Alkaline Plus",
                "เครื่องกรองน้ำเซฟ ผลิตน้ำดื่มอัลคาไลน์รุ่น Alkaline Plus ออกแบบให้เหมาะสมกับการใช้งานทั้งครอบครัวและออฟฟิศ",
                "http://www.safealkaline.com/media/catalog/product/cache/1/small_image/280x/9df78eab33525d08d6e5fb8d27136e95/a/l/alkaline_plus_1_1.jpg",
                BigDecimal.valueOf(Integer.parseInt("9500"))
        );
        productList.add(product3);

        view.setProduct(productList);
        view.onSuccess();
    }
}
