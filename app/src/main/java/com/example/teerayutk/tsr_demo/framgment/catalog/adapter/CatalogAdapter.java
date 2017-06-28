package com.example.teerayutk.tsr_demo.framgment.catalog.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.model.catalog.CatalogModel;
import com.example.teerayutk.tsr_demo.model.catalog.Product;
import com.example.teerayutk.tsr_demo.utils.ConvertToCurrency;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.shts.android.library.TriangleLabelView;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by teerayut.k on 8/6/2560.
 */

public class CatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean changeView;
    private final int VIEW_TYPE_GRID = 0;
    private final int VIEW_TYPE_LIST = 1;

    private Context context;
    private StringBuilder stringBuilder;
    private ClickListener clickListener;
    private List<Product> productList = new ArrayList<Product>();
    public CatalogAdapter(FragmentActivity activity, List<Product> productList) {
        this.context = activity;
        this.productList = productList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product, parent, false);
            changeView = true;
            viewHolder = new GridHolder(view);
        } else if (viewType == VIEW_TYPE_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_list, parent, false);
            changeView = false;
            viewHolder = new ListHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Product item = productList.get(position);
        stringBuilder = new StringBuilder();
        stringBuilder.append(item.getName());

        if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            Glide.with(context).load(item.getProductThumbs())
                    .placeholder(R.drawable.image_holder)
                    .into(gridHolder.productImg);
            gridHolder.productPrice.setText(ConvertToCurrency.Currency(item.getPrice() + "") + " " + context.getResources().getString(R.string.product_thai_bath));
            gridHolder.productDetail.setText(stringBuilder.toString());
        } else if (holder instanceof ListHolder) {
            ListHolder listHolder = (ListHolder) holder;
            Glide.with(context).load(item.getProductThumbs())
                    .placeholder(R.drawable.image_holder)
                    .into(listHolder.productImg);
            listHolder.productPrice.setText(ConvertToCurrency.Currency(item.getPrice() + "") + " " + context.getResources().getString(R.string.product_thai_bath));
            listHolder.productDetail.setText(stringBuilder.toString());
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return changeView ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setChangeViewFalse() {
        changeView = false;
    }

    public void setChangeViewTrue() {
        changeView = true;
    }

    public boolean getChangeView() {
        return changeView;
    }

    public interface ClickListener{
        public void addToCart(View view, int position);
    }

    public class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.product_thumbs) ImageView productImg;
        @Bind(R.id.product_price) TextView productPrice;
        @Bind(R.id.product_detail) TextView productDetail;
        @Bind(R.id.add_to_cart) Button addCart;
        public GridHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            addCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.addToCart(v, getPosition());
            }
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.product_thumbs) ImageView productImg;
        @Bind(R.id.product_price) TextView productPrice;
        @Bind(R.id.product_detail) TextView productDetail;
        @Bind(R.id.add_to_cart) Button addCart;
        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            addCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.addToCart(v, getPosition());
            }
        }
    }
}
