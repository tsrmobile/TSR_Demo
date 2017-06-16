package com.example.teerayutk.tsr_demo.activity.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teerayutk.tsr_demo.R;
import com.example.teerayutk.tsr_demo.utils.AnimateButton;
import com.example.teerayutk.tsr_demo.utils.Config;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUPActivity extends AppCompatActivity implements SignUPInterface.viewInterface, View.OnClickListener {
    private static final int PRIVACY = 05;
    private SweetAlertDialog sweetAlertDialog;
    private SignUPInterface.presentInterface present;

    private boolean email_policy = false;
    private boolean password_policy = false;
    private boolean password_confirm = false;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.button_apply) Button buttonApply;
    @Bind(R.id.first_name) EditText firstName;
    @Bind(R.id.last_name) EditText lastName;
    @Bind(R.id.email_address) EditText emailAddress;
    @Bind(R.id.password) EditText password;
    @Bind(R.id.confirm_password) EditText confirmPassword;
    @Bind(R.id.agreeTerm) CheckBox checkBox;
    @Bind(R.id.privacy) TextView textViewPrivacy;
    @Bind(R.id.email_error) TextView emailError;
    @Bind(R.id.hintPassword) TextView hintPass;
    @Bind(R.id.confirmPasswordStatus) TextView confirmStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        present = new SignUPPresenter(this);
        buttonApply.setOnClickListener(this);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.signup_activity_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String text = getResources().getString(R.string.terms_and_condition);
        String linkPrivacy = getResources().getString(R.string.privacy);
        int start = text.indexOf(linkPrivacy);
        int end = start + linkPrivacy.length();

        String linkCondition = getResources().getString(R.string.condition);
        int start2 = text.indexOf(linkCondition);
        int end2 = start2 + linkCondition.length();

        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new privacy(), start, end, 0);
        spannableString.setSpan(new condition(), start2, end2, 0);

        textViewPrivacy.setText(spannableString);
        textViewPrivacy.setMovementMethod(new LinkMovementMethod());

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(password.getText().toString()) && !password.getText().toString().equals("")) {
                    password_confirm = true;
                    confirmStatus.setText(getResources().getString(R.string.form_confirm_password_status_valid));
                    confirmStatus.setTextColor(getResources().getColor(R.color.Green));
                } else {
                    password_confirm = false;
                    confirmStatus.setText(getResources().getString(R.string.form_confirm_password_status_invalid));
                    confirmStatus.setTextColor(getResources().getColor(R.color.Red));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Pattern.matches(Config.EMAIL_REGEX, s.toString())) {
                    email_policy = true;
                    emailError.setText("");
                } else {
                    email_policy = false;
                    emailError.setText(getResources().getString(R.string.form_confirm_email_invalid));
                    emailError.setTextColor(getResources().getColor(R.color.Red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches(Config.PASSWORD_REGEX)) {
                    password_policy = true;
                    hintPass.setTextColor(getResources().getColor(R.color.Black));
                } else {
                    password_policy = false;
                    hintPass.setTextColor(getResources().getColor(R.color.Red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches(Config.NAME_REGEX)) {
                    Log.e("Name", "Yes");
                } else {
                    Log.e("Name", "No");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_apply :
                buttonApply.startAnimation(new AnimateButton().animbutton());
                getInformationForm();
                break;
            default: break;
        }
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
    public void onSuccess(String success) {

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

    private class privacy extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            startActivityForResult(new Intent(getApplicationContext(), PrivacyActivity.class), PRIVACY);
        }
    }

    private class condition extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            startActivityForResult(new Intent(getApplicationContext(), PrivacyActivity.class), PRIVACY);
        }
    }

    private void getInformationForm() {

        if (!email_policy || !password_policy || !password_confirm) {
            dialogShow(SweetAlertDialog.ERROR_TYPE,
                    getResources().getString(R.string.dialog_title_warning),
                    getResources().getString(R.string.form_error_dialog));
        } else {
            dialogShow(SweetAlertDialog.PROGRESS_TYPE, getResources().getString(R.string.dialog_title_loading), "");
        }
    }
}
