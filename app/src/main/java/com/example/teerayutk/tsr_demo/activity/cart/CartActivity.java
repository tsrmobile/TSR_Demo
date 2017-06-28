package com.example.teerayutk.tsr_demo.activity.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.model.cart.CartItem;
import com.example.teerayutk.tsr_demo.utils.ExtactCartItem;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CartActivity extends AppCompatActivity implements CartAdapter.ClickListener {

    private float sumtotal = 0;
    private CartAdapter adapter;
    private SweetAlertDialog sweetAlertDialog;
    private Cart cart = CartHelper.getCart();
    private List<CartItem> cartItemList = Collections.emptyList();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.total_price) TextView totalPrice;
    @Bind(R.id.recyclerview_cart) RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartItemList = new ExtactCartItem().getCartItems(cart);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CartActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new CartAdapter(CartActivity.this, cartItemList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        getTotalPrice();
    }

    private void getTotalPrice() {
        for (int i = 0; i < cartItemList.size(); i++) {
            CartItem item = cartItemList.get(i);
            sumtotal += (Float.parseFloat(String.valueOf(item.getProduct().getPrice().setScale(2, BigDecimal.ROUND_HALF_UP))) * item.getQuantity());
        }
        totalPrice.setText(String.valueOf(sumtotal));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                setResult(RESULT_CANCELED);
                finish();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void up(View view, int position) {

    }

    @Override
    public void down(View view, int position) {

    }

    @Override
    public void trash(View view, final int position) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No!")
                .setConfirmText("Yes,delete it!")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        cart.remove(cartItemList.get(position).getProduct());
                        cartItemList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, cartItemList.size());
                        adapter.notifyDataSetChanged();
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }
}
