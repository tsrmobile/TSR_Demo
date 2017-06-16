package com.example.teerayutk.tsr_demo.activity.signup;

import java.util.List;
import java.util.Objects;

/**
 * Created by teerayut.k on 5/6/2560.
 */

public interface SignUPInterface {

    interface viewInterface{
        void onSuccess(String success);
        void onFail(String fail);
        void showProgress();
        void dismissProgress();
    }

    interface presentInterface{
        void add(List<Objects> values);
    }
}
