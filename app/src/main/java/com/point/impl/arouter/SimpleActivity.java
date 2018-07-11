package com.point.impl.arouter;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.point.impl.R;

@Route(path = Const.ACTIVITY_URL_SIMPLE)
public class SimpleActivity extends BaseActivity {

    @Autowired()
    String name;

    @Autowired()
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        TextView textView = findViewById(R.id.tv_simple);
        String text = "name: " + name + ", age: " + age;
        textView.setText(text);
    }

}
