package com.point.impl.arouter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by licong12 on 2018/7/11.

 https://github.com/alibaba/ARouter/blob/master/README_CN.md

 功能介绍

 支持直接解析标准URL进行跳转，并自动注入参数到目标页面中
 支持多模块工程使用
 支持添加多个拦截器，自定义拦截顺序
 支持依赖注入，可单独作为依赖注入框架使用
 支持InstantRun
 支持MultiDex(Google方案)
 映射关系按组分类、多级管理，按需初始化
 支持用户指定全局降级与局部降级策略
 页面、拦截器、服务等组件均自动注册到框架
 支持多种方式配置转场动画
 支持获取Fragment
 完全支持Kotlin以及混编(配置见文末 其他#5)
 支持第三方 App 加固(使用 arouter-register 实现自动注册)

 */

public class App extends Application {

    private boolean isDebugARouter = true;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isDebugARouter) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

    }

}
