package com.example.teerayutk.tsr_demo.activity.signup;

import java.util.List;
import java.util.Objects;

/**
 * Created by teerayut.k on 5/6/2560.
 */

public class SignUPPresenter implements SignUPInterface.presentInterface {

    private SignUPInterface.viewInterface view;
    public SignUPPresenter(SignUPInterface.viewInterface view){
        this.view = view;
    }

    @Override
    public void add(List<Objects> values) {

    }
}
