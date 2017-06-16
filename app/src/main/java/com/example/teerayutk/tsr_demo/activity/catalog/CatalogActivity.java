package com.example.teerayutk.tsr_demo.activity.catalog;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.activity.authentication.AuthenticationActivity;
import com.example.teerayutk.tsr_demo.activity.signup.SignUPActivity;
import com.example.teerayutk.tsr_demo.framgment.catalog.CatalogFragment;
import com.example.teerayutk.tsr_demo.utils.ActivityResultBus;
import com.example.teerayutk.tsr_demo.utils.ActivityResultEvent;
import com.example.teerayutk.tsr_demo.utils.AnimateButton;
import com.example.teerayutk.tsr_demo.utils.Config;
import com.example.teerayutk.tsr_demo.utils.LanguageHelper;
import com.example.teerayutk.tsr_demo.utils.MyApplication;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CatalogActivity extends AppCompatActivity{

    private static final int SIGN_UP = 01;
    private MenuItem menuItemClicked;
    private ActionBarDrawerToggle drawerToggle;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.navigationView) NavigationView navigationView;
    @Bind(R.id.DrawerLayout) DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        LanguageHelper.loadLocale(getBaseContext().getResources());
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        navigationDrawerMenu();

        if (savedInstanceState == null) {
            loadHomePage();
        }
    }

    private void loadHomePage() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_frame, new CatalogFragment()).addToBackStack(null).commit();
    }

    private void navigationDrawerMenu() {
        LayoutInflater inflater = getLayoutInflater();
        //----- set Header menu -----//
        View header = inflater.inflate(R.layout.menu_header, null, false);
        navigationView.addHeaderView(header);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(menuItemClicked != null) {
                    selectDrawerItem(menuItemClicked);
                }
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        navigationView.inflateMenu(R.menu.main_menu);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItemClicked = menuItem;
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        navigationView.getMenu().getItem(0).setChecked(true);
        toolbar.setTitle(navigationView.getMenu().getItem(0).getTitle());
        setSupportActionBar(toolbar);
        drawerToggle.syncState();

        handleMenu(navigationView.getMenu());
    }

    private void handleMenu(Menu menu) {
        try {
            if (MyApplication.getInstance().getPrefManager().getPreferrence(Config.KEY_REMEMBER_ME).equals("false")) {
                menu.setGroupVisible(R.id.system, false);
                MenuItem item = menu.findItem(R.id.menu_profile);
                item.setVisible(false);
            }
        } catch (Exception e) {
            Log.e("Catalog Menu", e.getMessage());
            menu.setGroupVisible(R.id.system, false);
            MenuItem item = menu.findItem(R.id.menu_profile);
            item.setVisible(false);
        }
    }

    public void selectDrawerItem(MenuItem menuItem) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        switch(menuItem.getItemId()) {
            case R.id.menu_product :
                if (currentFragment instanceof CatalogFragment) {
                    drawerLayout.closeDrawers();
                } else {
                    transaction.replace(R.id.content_frame, new CatalogFragment(), "CatalogFragment").addToBackStack(null).commit();
                }
                break;
            case R.id.menu_registration :
                startActivityForResult(new Intent(getApplicationContext(), SignUPActivity.class), SIGN_UP);
                break;
            case R.id.menu_logout :
                startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                MyApplication.getInstance().getPrefManager().setPreferrence(Config.KEY_REMEMBER_ME, "false");
                break;
            default:
                loadHomePage();
                break;
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_cart :
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
        if (requestCode == SIGN_UP && resultCode == RESULT_CANCELED) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
}
