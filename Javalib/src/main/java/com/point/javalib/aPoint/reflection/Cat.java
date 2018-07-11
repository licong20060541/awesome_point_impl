package com.point.javalib.aPoint.reflection;

import com.point.javalib.zPoint.Utils;

/**
 * Created by licong12 on 2018/7/11.
 */

public class Cat {

    private String name;

    @Deprecated
    public int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void eat(String food) {
        Utils.print("eat food " + food);
    }

    public void eat(String... foods) {
        StringBuilder s = new StringBuilder();
        for (String food : foods) {
            s.append(food);
            s.append(" ");
        }
        Utils.print("eat food " + s.toString());
    }

    private void sleep() {
        Utils.print("sleep");
    }

    @Override
    public String toString() {
        return "name = " + name + " age = " + age;
    }

}
