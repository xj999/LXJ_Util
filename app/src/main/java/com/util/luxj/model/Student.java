package com.util.luxj.model;

import org.litepal.crud.DataSupport;

/**
 * Created by Luxj on 2015/7/13.
 */
public class Student extends DataSupport {
    private long id;
    private String name;
    private int age;
    private int sex;
    private String desc;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
