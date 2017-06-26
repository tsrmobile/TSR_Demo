package com.example.teerayutk.tsr_demo.framgment.catalog;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.activity.cart.CartActivity;
import com.example.teerayutk.tsr_demo.activity.catalog.CatalogActivity;
import com.example.teerayutk.tsr_demo.activity.catalog.CatalogDetailActivity;
import com.example.teerayutk.tsr_demo.model.catalog.Product;
import com.example.teerayutk.tsr_demo.framgment.catalog.adapter.CatalogAdapter;
import com.example.teerayutk.tsr_demo.model.cart.CartItem;
import com.example.teerayutk.tsr_demo.model.catalog.CatalogModel;
import com.example.teerayutk.tsr_demo.utils.ActivityResultBus;
import com.example.teerayutk.tsr_demo.utils.ActivityResultEvent;
import com.example.teerayutk.tsr_demo.utils.AnimateButton;
import com.example.teerayutk.tsr_demo.utils.ExtactCartItem;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment implements View.OnClickListener,
        CatalogAdapter.ClickListener, CatalogInterface.viewInterface {


    public CatalogFragment() {
        // Required empty public constructor
    }

    private int count = 0;
    private static final int FILTER = 02;
    private static final int CART = 03;
    private static final int PRODUCT_DETAIL = 04;
    private static final int SHOPPING_CART = 06;

    private Product product;
    private int badgeQuantity = 0;
    private CatalogModel catalogModel;
    private Cart cart = CartHelper.getCart();
    private List<CartItem> cartItemList = Collections.emptyList();
    private List<Product> productList = new ArrayList<Product>();

    private CatalogAdapter adapter;
    private SweetAlertDialog sweetAlertDialog;
    private CatalogInterface.presentInterface present;

    @Bind(R.id.fabBtn) FloatingActionButton viewMode;
    @Bind(R.id.recyclerviewProduct) RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);
        //setHasOptionsMenu(true);
        present = new CatalogPresenter(this);
        viewMode.setOnClickListener(this);

        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.DeepPink,
                R.color.colorPrimaryDark,
                R.color.LimeGreen);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(null);
                present.loadProduct();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.shopping_cart, menu);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.counter_menuitem_layout);
        RelativeLayout badgeLayout = (RelativeLayout) MenuItemCompat.getActionView(item);
        ImageView imageView = (ImageView) badgeLayout.findViewById(R.id.counterBackground);
        TextView textView = (TextView) badgeLayout.findViewById(R.id.count);
        cartItemList = new ExtactCartItem().getCartItems(cart);

        if (cartItemList.size() > 0) {
            for (int i = 0; i < cartItemList.size(); i ++) {
                final CartItem cartItem = cartItemList.get(i);
                badgeQuantity += Integer.parseInt(cartItem.getQuantity() + "");
            }
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.badge_cart_item));
            textView.setText("" + badgeQuantity);
            /*YoYo.with(Techniques.Tada)
                    .duration(500)
                    .playOn(badgeLayout);*/
        } else {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }

        badgeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(getActivity(), CartActivity.class), SHOPPING_CART);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtn :
                viewMode.startAnimation(new AnimateButton().animbutton());
                viewType();
                break;
            default: break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRODUCT_DETAIL && resultCode == RESULT_CANCELED) {
            //getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        present.loadProduct();
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    private void viewType() {
        adapter.notifyDataSetChanged();
        if (adapter.getChangeView()) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            adapter.setClickListener(this);
            viewMode.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_view_module_white_48dp));
            adapter.setChangeViewFalse();
        } else {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.setClickListener(this);
            viewMode.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_view_list_white_48dp));
            adapter.setChangeViewTrue();
        }
    }

    @Override
    public void itemClicked(View view, int position) {
    }

    @Override
    public void addCart(View view, int position) {
        Product product = productList.get(position);
        doIncrease(product);
    }

    @Override
    public void viewDetail(View view, int position) {
        Product product = productList.get(position);
        Intent intent = new Intent(getActivity(), CatalogDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        intent.putExtras(bundle);
        startActivityForResult(intent, PRODUCT_DETAIL);
    }

    @Override
    public void onLoad() {
        dialogShow(SweetAlertDialog.PROGRESS_TYPE, getResources().getString(R.string.dialog_title_loading), "");
    }

    @Override
    public void onSuccess() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            viewMode.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_view_module_white_48dp));
        }

        if (sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
    }

    @Override
    public void onFail(String fail) {
        dialogShow(SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.dialog_title_warning), fail);
    }

    @Override
    public void setProduct(List<Product> productList) {
        this.productList = productList;
        adapter = new CatalogAdapter(getActivity(), productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        adapter.addCartListener(this);
        adapter.viewDetailListener(this);
    }

    public void dialogShow(int type, String title, String msg) {
        sweetAlertDialog = new SweetAlertDialog(getActivity(), type);
        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    private void doIncrease(Product product) {
        cart.add(product, 1);
        badgeQuantity = 0;
        getActivity().invalidateOptionsMenu();
    }
}
