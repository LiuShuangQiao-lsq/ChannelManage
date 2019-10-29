package com.tb.demo.pojo;

public class PropertyManage {
    private int id;
    private String propertyName;//存入数据库的属性名
    private String propertyShow;//展示的属性名
    private String propertyTips;//提示

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyShow() {
        return propertyShow;
    }

    public void setPropertyShow(String propertyShow) {
        this.propertyShow = propertyShow;
    }

    public String getPropertyTips() {
        return propertyTips;
    }

    public void setPropertyTips(String propertyTips) {
        this.propertyTips = propertyTips;
    }

}
