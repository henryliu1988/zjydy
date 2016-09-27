package com.zhjydy.model.entity;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class NormalDicItem {

    private String id;
    private String name;

    public NormalDicItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
