package com.util.luxj.model;

/**
 * 题目类
 * Created by Luxj on 2015/8/7 15:40
 */
public class TaskType {
    private int type;
    private String id;
    private int order;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public TaskType(int type, String id, int order) {
        super();

        this.type = type;
        this.order = order;
        this.id = id;
    }


}
