package com.example.teerayutk.tsr_demo.activity.catalog;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.activity.catalog.adapter.ImageAdapter;
import com.example.teerayutk.tsr_demo.model.cart.CartItem;
import com.example.teerayutk.tsr_demo.model.catalog.Product;
import com.example.teerayutk.tsr_demo.utils.AnimateButton;
import com.example.teerayutk.tsr_demo.utils.ConvertToCurrency;
import com.example.teerayutk.tsr_demo.utils.ExtactCartItem;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.relex.circleindicator.CircleIndicator;

public class CatalogDetailActivity extends SwipeBackActivity implements View.OnClickListener {
    private static final String TAG = CatalogDetailActivity.class.getSimpleName();

    private int item_amount = 0;
    private int badgeQuantity = 0;
    private float initialX, initialY;
    private SweetAlertDialog sweetAlertDialog;

    Product product;
    Cart cart = CartHelper.getCart();
    List<CartItem> cartItemList = Collections.emptyList();
    List<String> imageItemList = new ArrayList<String>();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.minus) Button btnMinus;
    @Bind(R.id.plus) Button btnPlus;
    @Bind(R.id.txtAmount) TextView amount;
    @Bind(R.id.price) TextView price;
    @Bind(R.id.detail) TextView detail;
    @Bind(R.id.addToCart) Button addCart;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.indicator) CircleIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_detail);
        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);
        ButterKnife.bind(this);
        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        addCart.setOnClickListener(this);

        initWidget();
    }

    private void initWidget() {
        readIntent();
        toolbar.setTitle(product.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageItemList.add(product.getProductThumbs().toString());
        viewPager.setAdapter(new ImageAdapter(CatalogDetailActivity.this, imageItemList));
        indicator.setViewPager(viewPager);

        price.setText("฿" + ConvertToCurrency.Currency(product.getPrice().toString()) + ".-");

        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append("\n" + product.getName() + "\n\n");
        sb.append(getResources().getString(R.string.product_title_detail) + "\n\t\t" + product.getProductDesc());
        detail.setText(sb.toString());
    }

    private void readIntent() {
        try {
            Bundle data = this.getIntent().getExtras();
            product = (Product) data.getSerializable("product");
        } catch (Exception e) {
            Log.e("Null item", e.toString() + "\n" + product.getProductThumbs());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_cart, menu);
        MenuItem item = menu.findItem(R.id.action_cart);

        MenuItemCompat.setActionView(item, R.layout.counter_menuitem_layout);
        RelativeLayout badgeLayout = (RelativeLayout) MenuItemCompat.getActionView(item);
        ImageView imageView = (ImageView) badgeLayout.findViewById(R.id.counterBackground);
        TextView textViewCount = (TextView) badgeLayout.findViewById(R.id.count);

        cartItemList = new ExtactCartItem().getCartItems(cart);

        if (cartItemList.size() > 0) {
            for (int i = 0; i < cartItemList.size(); i ++) {
                final CartItem cartItem = cartItemList.get(i);
                badgeQuantity += Integer.parseInt(cartItem.getQuantity() + "");
            }
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.badge_cart_item));
            textViewCount.setText("" + badgeQuantity);
        } else {
            imageView.setVisibility(View.GONE);
            textViewCount.setVisibility(View.GONE);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        badgeQuantity = 0;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minus :
                btnMinus.startAnimation(new AnimateButton().animbutton());
                decreas();
                break;
            case R.id.plus :
                btnPlus.startAnimation(new AnimateButton().animbutton());
                increase();
                break;
            case R.id.addToCart :
                addCart.startAnimation(new AnimateButton().animbutton());
                addtocart();
                break;
            default: break;
        }
    }

    private void addtocart() {
        item_amount = Integer.parseInt(amount.getText().toString());
        if (item_amount > 0) {
            cart.add(product, item_amount);
            badgeQuantity = 0;
            invalidateOptionsMenu();
        } else {
            dialogShow(SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.dialog_title_warning), getResources().getString(R.string.add_cart_warning));
        }
    }

    private void increase() {
        item_amount = Integer.parseInt(amount.getText().toString());
        item_amount++;
        amount.setText(String.valueOf(item_amount));
    }

    private void decreas() {
        item_amount = Integer.parseInt(amount.getText().toString());
        if (item_amount > 0)
            item_amount--;
        amount.setText(String.valueOf(item_amount));
    }

    public void dialogShow(int type, String title, String msg) {
        sweetAlertDialog = new SweetAlertDialog(this, type);
        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }
}
