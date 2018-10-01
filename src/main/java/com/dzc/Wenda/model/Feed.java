package com.dzc.Wenda.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Feed {
    private int id;
    private int type;
    private int userId;
    private Date createDate;
    private String data;
    private JSONObject dataJSON = null;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        dataJSON = JSONObject.parseObject(data);
    }

    public JSONObject getDataJSON() {
        return dataJSON;
    }

    public void setDataJSON(JSONObject dataJSON) {
        this.dataJSON = dataJSON;
    }


    //方便Velocity模版解析Feed对应的data里的数据
    public String get(String key) {
        return dataJSON == null ? null : dataJSON.getString(key);
    }


    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", type=" + type +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", data='" + data + '\'' +
                ", dataJSON=" + dataJSON +
                '}';
    }
}
