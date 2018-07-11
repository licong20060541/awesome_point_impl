package com.point.impl.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Created by licong12 on 2018/7/11.
 */

@Interceptor(priority = 1)
public class UserInterceptor implements IInterceptor {

    @Override
    public void init(Context context) {
        Const.log(UserInterceptor.class.getCanonicalName() + ":init()");
    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Const.log(UserInterceptor.class.getCanonicalName()
                + ":process(), "
                + Thread.currentThread().getName());
    }

}
