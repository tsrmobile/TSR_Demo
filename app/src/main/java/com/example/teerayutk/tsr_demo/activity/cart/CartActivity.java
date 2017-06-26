package com.example.teerayutk.tsr_demo.activity.cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.model.cart.CartItem;
import com.example.teerayutk.tsr_demo.utils.ExtactCartItem;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    CartAdapter adapter;
    Cart cart = CartHelper.getCart();
    List<CartItem> cartItemList = Collections.emptyList();

    @Bind(R.id.toolbar) Toolbar toolbar;
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
}
