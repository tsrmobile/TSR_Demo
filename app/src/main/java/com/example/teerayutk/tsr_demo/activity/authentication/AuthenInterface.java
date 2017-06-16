package com.example.teerayutk.tsr_demo.activity.authentication;

/**
 * Created by teerayut.k on 2/6/2560.
 */

public interface AuthenInterface {

    interface viewInterface{
        void onSuccess(String success);
        void onFail(String fail);
        void showProgress();
        void dismissProgress();
    }

    interface presentInterface{
        void authen(String username, String password, boolean remember);
    }
}
