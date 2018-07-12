package com.point.impl.arouter;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Created by licong12 on 2018/7/11.
 */

@Interceptor(priority = 7)
public class UserSecondInterceptor implements IInterceptor {

    @Override
    public void init(Context context) {
        Const.log(UserSecondInterceptor.class.getCanonicalName() + ":init()");
    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Const.log(UserSecondInterceptor.class.getCanonicalName()
                + ":process(), "
                + Thread.currentThread().getName());
        if (TextUtils.equals(postcard.getPath(), Const.ACTIVITY_URL_SIMPLE)) {
            Const.log(UserSecondInterceptor.class.getCanonicalName() + ", 拦截处理");
        }
        callback.onContinue(postcard);
    }
    
}
