package com.example.teerayutk.tsr_demo.activity.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.activity.catalog.CatalogActivity;
import com.example.teerayutk.tsr_demo.activity.signup.SignUPActivity;
import com.example.teerayutk.tsr_demo.utils.AnimateButton;
import com.example.teerayutk.tsr_demo.utils.LanguageHelper;
import com.example.teerayutk.tsr_demo.utils.MyApplication;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AuthenticationActivity extends AppCompatActivity implements AuthenInterface.viewInterface, View.OnClickListener {

    private String DEFUALT_LANGUAGE;
    private SweetAlertDialog sweetAlertDialog;
    private AuthenInterface.presentInterface present;

    private boolean rememberme = false;
    private static final int SIGN_UP = 01;
    private static final String LANGUAGE = "language";

    @Bind(R.id.user_name) EditText userName;
    @Bind(R.id.user_pwd) EditText userPwd;
    @Bind(R.id.sign_up) TextView buttonSignup;
    @Bind(R.id.skip) TextView buttonSkip;
    @Bind(R.id.button_sign_in) Button buttonSignin;
    @Bind(R.id.button_language) Button changeLanguage;
    @Bind(R.id.remember) CheckBox rememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        LanguageHelper.loadLocale(getBaseContext().getResources());
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        present = new AuthenPresenter(this);
        buttonSkip.setOnClickListener(this);
        buttonSignin.setOnClickListener(this);
        buttonSignup.setOnClickListener(this);
        changeLanguage.setOnClickListener(this);
        rememberMe.setOnClickListener(this);

        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    userPwd.requestFocus();
                    return true;
                }
                return false;
            }
        });

        userPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(userPwd.getWindowToken(), 0);
                    buttonSignin.performClick();
                    return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            loadLanguageSettings();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LANGUAGE, MyApplication.getInstance().getPrefManager().getLanguage());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString(LANGUAGE).equals("en")) {
            setButtonTH();
            LanguageHelper.changeLocale(this.getResources(), savedInstanceState.getString(LANGUAGE));
            MyApplication.getInstance().getPrefManager().setLanguage(savedInstanceState.getString(LANGUAGE));
            reload();
        } else {
            setButtonEN();
            LanguageHelper.changeLocale(this.getResources(), savedInstanceState.getString(LANGUAGE));
            MyApplication.getInstance().getPrefManager().setLanguage(savedInstanceState.getString(LANGUAGE));
            reload();
        }
        Log.e("Restore language ",  savedInstanceState.getString(LANGUAGE));
    }

    @Override
    public void onSuccess(String success) {
        startActivity(new Intent(getApplicationContext(), CatalogActivity.class));
        finish();
    }

    @Override
    public void onFail(String fail) {
        dialogShow(SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.dialog_title_warning), fail);
    }

    @Override
    public void showProgress() {
        dialogShow(SweetAlertDialog.PROGRESS_TYPE, getResources().getString(R.string.dialog_title_loading), "");
    }

    @Override
    public void dismissProgress() {
        sweetAlertDialog.dismiss();
    }

    public void dialogShow(int type, String title, String msg) {
        sweetAlertDialog = new SweetAlertDialog(this, type);
        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in :
                buttonSignin.startAnimation(new AnimateButton().animbutton());
                present.authen(userName.getText().toString(), userPwd.getText().toString(), rememberme);
                break;
            case R.id.sign_up:
                buttonSignup.startAnimation(new AnimateButton().animbutton());
                startActivityForResult(new Intent(getApplicationContext(), SignUPActivity.class), SIGN_UP);
                break;
            case R.id.skip :
                buttonSkip.startAnimation(new AnimateButton().animbutton());
                startActivity(new Intent(getApplicationContext(), CatalogActivity.class));
                finish();
                break;
            case R.id.button_language :
                changeLanguage.startAnimation(new AnimateButton().animbutton());
                changeButtonLanguage(v);
                break;
            case R.id.remember :
                if (rememberMe.isChecked())
                    rememberme = true;
                else
                    rememberme = false;
                break;
            default: break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP && resultCode == RESULT_CANCELED) {
            Log.e("Sign up code : ", SIGN_UP + "");
        }
    }

    private void changeButtonLanguage(View v) {
        final int status = (Integer) v.getTag();
        switch (status) {
            case 0 :
                setButtonEN();
                LanguageHelper.changeLocale(this.getResources(), "th");
                MyApplication.getInstance().getPrefManager().setLanguage("th");
                reload();
                break;
            case 1 :
                setButtonTH();
                LanguageHelper.changeLocale(this.getResources(), "en");
                MyApplication.getInstance().getPrefManager().setLanguage("en");
                reload();
                break;
            default: break;
        }
    }

    private void setButtonTH() {
        changeLanguage.setText("TH");
        changeLanguage.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
        changeLanguage.setBackgroundResource(R.drawable.circle_shape);
        changeLanguage.setTag(0);
    }

    private void setButtonEN() {
        changeLanguage.setText("EN");
        changeLanguage.setTextColor(getApplication().getResources().getColor(R.color.White));
        changeLanguage.setBackgroundResource(R.drawable.border_shape);
        changeLanguage.setTag(1);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(intent);
    }

    private void loadLanguageSettings() {
        try {
            if (MyApplication.getInstance().getPrefManager().getLanguage().equals("th")) {
                setButtonEN();
                LanguageHelper.changeLocale(getBaseContext().getResources(), MyApplication.getInstance().getPrefManager().getLanguage());
                Log.e("Languages ", MyApplication.getInstance().getPrefManager().getLanguage());
            } else if (MyApplication.getInstance().getPrefManager().getLanguage().equals("en")) {
                setButtonTH();
                LanguageHelper.changeLocale(getBaseContext().getResources(),MyApplication.getInstance().getPrefManager().getLanguage());
                Log.e("Languages ", MyApplication.getInstance().getPrefManager().getLanguage());
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "Get settings : " + ex.toString());
            initDefualtLanguage();
        }
    }

    private void initDefualtLanguage() {
        DEFUALT_LANGUAGE = Locale.getDefault().getDisplayLanguage();
        if (DEFUALT_LANGUAGE.equals("ไทย")) {
            setButtonEN();
            MyApplication.getInstance().getPrefManager().setLanguage("th");
        } else if (DEFUALT_LANGUAGE.equals("English")) {
            setButtonTH();
            MyApplication.getInstance().getPrefManager().setLanguage("en");
        }
    }
}
