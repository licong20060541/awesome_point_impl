package com.point.impl.arouter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
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
                        .build(Const.ACTIVITY_URL_SIMPLE, Const.GROUP_FIRST)
                        .withString("name", "licong")
                        .withInt("age", 23)
//                        .withTransition()
//                        .navigation(RouterActivity.this, 123)
                        .navigation(RouterActivity.this, new NavCallback() {

                            @Override
                            public void onFound(Postcard postcard) {
                                super.onFound(postcard);
                                Const.log("onFound");
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                Const.log("onArrival");
                            }
                        });

//                Uri uri = Uri.parse(Const.ACTIVITY_URL_PARSE);
//                ARouter.getInstance().build(uri).navigation();

//                Fragment fragment = (Fragment) ARouter.getInstance()
//                        .build(Const.ACTIVITY_URL_FRAGMENT)
//                        .navigation();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 110:
                Const.log("110: onActivityResult");
                break;
        }
    }
}
