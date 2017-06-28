package com.example.teerayutk.tsr_demo.activity.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by teerayut.k on 6/26/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ClickListener clickListener;
    private List<CartItem> cartItemList = Collections.emptyList();
    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItemList = cartItems;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_cart, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        final CartItem cartItem = cartItemList.get(position);
        holder.name.setText(cartItem.getProduct().getName());
        holder.price.setText(String.valueOf(cartItem.getProduct().getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
        holder.amount.setText(String.valueOf(cartItem.getQuantity()));
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.product_name) TextView name;
        @Bind(R.id.product_price) TextView price;
        @Bind(R.id.product_amount) TextView amount;
        @Bind(R.id.btn_increase) ImageView increase;
        @Bind(R.id.btn_decrease) ImageView decrease;
        @Bind(R.id.btn_delete_item) ImageView delete;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            increase.setOnClickListener(this);
            decrease.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_increase) {
                clickListener.up(v, getPosition());
            } else if (v.getId() == R.id.btn_decrease) {
                clickListener.down(v, getPosition());
            } else if (v.getId() == R.id.btn_delete_item) {
                clickListener.trash(v, getPosition());
            }
        }
    }

    public interface ClickListener{
        public void up(View view, int position);
        public void down(View view, int position);
        public void trash(View view, int position);
    }
}
