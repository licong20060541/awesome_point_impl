package com.point.makejar;

/**
 * Created by licong12 on 2018/7/11.
 */

public class Cat {

    private String name;

    public int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void eat(String food) {
        System.out.println("eat food " + food);
    }

    public void eat(String... foods) {
        StringBuilder s = new StringBuilder();
        for (String food : foods) {
            s.append(food);
            s.append(" ");
        }
        System.out.println("eat food... ");
    }

    private void sleep() {
        System.out.println("sleep");
    }

    @Override
    public String toString() {
        return "name = " + name + " age = " + age;
    }

}
