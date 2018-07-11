package com.point.impl.arouter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.point.impl.R;

/**
 * Created by licong12 on 2018/7/11.
 */

@Route(path = Const.ACTIVITY_URL_MAIN)
public class RouterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arouter_main);
        findViewById(R.id.tv_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build(Const.ACTIVITY_URL_SIMPLE)
                        .withString("name", "licong")
                        .withInt("age", 23)
//                        .withTransition()
                        .navigation();

//                Uri uri = Uri.parse(Const.ACTIVITY_URL_PARSE);
//                ARouter.getInstance().build(uri).navigation();

//                Fragment fragment = (Fragment) ARouter.getInstance()
//                        .build(Const.ACTIVITY_URL_FRAGMENT)
//                        .navigation();

            }
        });
    }

}
