package com.example.teerayutk.tsr_demo.framgment.catalog.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ClickListener cartListener;
    private ClickListener viewListener;
    private List<Product> productList = new ArrayList<Product>();
    public CatalogAdapter(FragmentActivity activity, List<Product> productList) {
        this.context = activity;
        this.productList = productList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_grid, parent, false);
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
        //stringBuilder.append(context.getResources().getString(R.string.product_title_detail) + " : " + item.getDescription());

        if (holder instanceof GridHolder) {
            GridHolder gridHolder = (GridHolder) holder;
            Glide.with(context).load(item.getProductThumbs())
                    .placeholder(R.drawable.image_holder)
                    .into(gridHolder.productImg);
            gridHolder.productPrice.setText(context.getResources().getString(R.string.product_title_price) + " " + ConvertToCurrency.Currency(item.getPrice() + ""));
            gridHolder.productDetail.setText(stringBuilder.toString());
            if (!item.getProductID().equals("1")) {
                gridHolder.triangleLabelView.setVisibility(View.GONE);
            } else if (item.getProductID() == "1") {
                gridHolder.triangleLabelView.setVisibility(View.VISIBLE);
            }

        } else if (holder instanceof ListHolder) {
            ListHolder listHolder = (ListHolder) holder;

            Glide.with(context).load(item.getProductThumbs())
                    .placeholder(R.drawable.image_holder)
                    .into(listHolder.productImg);
            listHolder.productPrice.setText(context.getResources().getString(R.string.product_title_price) + " " + ConvertToCurrency.Currency(item.getPrice() + ""));
            listHolder.productDetail.setText(stringBuilder.toString());
            if (!item.getProductID().equals("1")) {
                listHolder.triangleLabelView.setVisibility(View.GONE);
            } else if (item.getProductID() == "1") {
                listHolder.triangleLabelView.setVisibility(View.VISIBLE);
            }
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

    public void addCartListener(ClickListener clickListener) {
        this.cartListener = clickListener;
    }

    public void viewDetailListener(ClickListener clickListener) {
        this.viewListener = clickListener;
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
        public void itemClicked(View view, int position);
        public void addCart(View view, int position);
        public void viewDetail(View view, int position);
    }

    public class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.product_thumbs_grid) ImageView productImg;
        @Bind(R.id.product_price) TextView productPrice;
        @Bind(R.id.product_detail) TextView productDetail;
        @Bind(R.id.ribbinNew) TriangleLabelView triangleLabelView;
        @Bind(R.id.blur_layout) BlurLayout blurLayout;
        public GridHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //itemView.setOnClickListener(this);
            BlurLayout.setGlobalDefaultDuration(500);
            View hover = LayoutInflater.from(context).inflate(R.layout.hover_cardveiw, null);
            ImageView addCart = (ImageView) hover.findViewById(R.id.add_cart);
            addCart.setOnClickListener(this);

            ImageView viewDetail = (ImageView) hover.findViewById(R.id.view_detail);
            viewDetail.setOnClickListener(this);

            blurLayout.setHoverView(hover);
            blurLayout.setBlurDuration(550);
            blurLayout.setBlurRadius(20);
            blurLayout.addChildAppearAnimator(hover, R.id.add_cart, Techniques.FlipInX, 550, 0);
            blurLayout.addChildAppearAnimator(hover, R.id.view_detail, Techniques.FlipInX, 550, 250);

            blurLayout.addChildDisappearAnimator(hover, R.id.add_cart, Techniques.FlipOutX, 550, 500);
            blurLayout.addChildDisappearAnimator(hover, R.id.view_detail, Techniques.FlipOutX, 550, 250);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

            switch (v.getId()) {
                case R.id.add_cart :
                    if (cartListener != null) {
                        cartListener.addCart(v, getPosition());
                        YoYo.with(Techniques.Tada)
                                .duration(500)
                                .playOn(v);
                    }
                    break;
                case R.id.view_detail :
                    if (viewListener != null) {
                        viewListener.viewDetail(v, getPosition());
                        YoYo.with(Techniques.Tada)
                                .duration(500)
                                .playOn(v);
                    }
                    break;
            }
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.product_thumbs_list) ImageView productImg;
        @Bind(R.id.product_price) TextView productPrice;
        @Bind(R.id.product_detail) TextView productDetail;
        @Bind(R.id.ribbinNew) TriangleLabelView triangleLabelView;
        @Bind(R.id.blur_layout) BlurLayout blurLayout;
        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            BlurLayout.setGlobalDefaultDuration(500);
            View hover = LayoutInflater.from(context).inflate(R.layout.hover_cardveiw, null);
            ImageView addCart = (ImageView) hover.findViewById(R.id.add_cart);
            addCart.setOnClickListener(this);

            ImageView viewDetail = (ImageView) hover.findViewById(R.id.view_detail);
            viewDetail.setOnClickListener(this);

            blurLayout.setHoverView(hover);
            blurLayout.setBlurDuration(550);
            blurLayout.setBlurRadius(20);
            blurLayout.addChildAppearAnimator(hover, R.id.add_cart, Techniques.FlipInX, 550, 0);
            blurLayout.addChildAppearAnimator(hover, R.id.view_detail, Techniques.FlipInX, 550, 250);

            blurLayout.addChildDisappearAnimator(hover, R.id.add_cart, Techniques.FlipOutX, 550, 500);
            blurLayout.addChildDisappearAnimator(hover, R.id.view_detail, Techniques.FlipOutX, 550, 250);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }

            switch (v.getId()) {
                case R.id.add_cart :
                    if (cartListener != null) {
                        cartListener.addCart(v, getPosition());
                        YoYo.with(Techniques.Tada)
                                .duration(550)
                                .playOn(v);
                    }
                    break;
                case R.id.view_detail :
                    if (viewListener != null) {
                        viewListener.viewDetail(v, getPosition());
                        YoYo.with(Techniques.Tada)
                                .duration(550)
                                .playOn(v);
                    }
                    break;
            }
        }
    }
}
