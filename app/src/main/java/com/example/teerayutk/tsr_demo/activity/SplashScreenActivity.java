package com.example.teerayutk.tsr_demo.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.activity.authentication.AuthenticationActivity;
import com.example.teerayutk.tsr_demo.activity.catalog.CatalogActivity;
import com.example.teerayutk.tsr_demo.utils.Config;
import com.example.teerayutk.tsr_demo.utils.MyApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Handler handler = new Handler();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (MyApplication.getInstance().getPrefManager().getPreferrence(Config.KEY_REMEMBER_ME).equals("false")) {
                                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            Log.e("SplashScreen", e.getMessage());
                            Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).start();
    }
}
