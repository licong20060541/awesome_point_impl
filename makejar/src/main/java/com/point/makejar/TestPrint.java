package com.point.makejar;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by licong12 on 2018/7/13.
 */

public class TestPrint {

    public void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void sout() {
        System.out.println("测试信息，已经调用了sout()方法");
    }

}
