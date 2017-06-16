package com.example.teerayutk.tsr_demo.activity.authentication;

import android.content.SharedPreferences;

import com.example.teerayutk.tsr_demo.utils.Config;
import com.example.teerayutk.tsr_demo.utils.MyApplication;
import com.example.teerayutk.tsr_demo.utils.MyPreferenceManager;

/**
 * Created by teerayut.k on 2/6/2560.
 */

public class AuthenPresenter implements AuthenInterface.presentInterface {

    private AuthenInterface.viewInterface view;

    public AuthenPresenter(AuthenInterface.viewInterface view) {
        this.view = view;
    }

    @Override
    public void authen(String username, String password, boolean remember) {
        view.showProgress();
        if (validate(username, password)) {
            if (remember) {
                MyApplication.getInstance().getPrefManager().setPreferrence(Config.KEY_REMEMBER_ME, ""+remember);
                MyApplication.getInstance().getPrefManager().setPreferrence(Config.KEY_USERNAME, username);
                MyApplication.getInstance().getPrefManager().setPreferrence(Config.KEY_PASSWORD, password);
            }
        } else {
            view.dismissProgress();
            view.onFail("Username or password is empty.");
        }
    }

    public boolean validate(String username, String password) {
        boolean valid = true;
        if (username.isEmpty())
            valid = false;

        if (password.isEmpty())
            valid = false;

        return valid;
    }
}
