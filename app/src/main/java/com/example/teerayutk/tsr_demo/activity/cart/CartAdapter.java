package com.example.teerayutk.tsr_demo.activity.cart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by teerayut.k on 6/26/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
