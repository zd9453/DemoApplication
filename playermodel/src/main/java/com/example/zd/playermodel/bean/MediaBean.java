package com.example.zd.playermodel.bean;

/**
 * use to do 视频信息
 *
 * @author zhangdong on 2018/3/6 0006.
 * @version 1.0
 * @see .
 * @since 1.0
 */

public class MediaBean {
    private String name;
    private long size;
    private long time;
    private String dateUrl;
    private String addTime;
    private String type;

    public MediaBean(String name, long size, long time, String dateUrl, String addTime, String type) {
        this.name = name;
        this.size = size;
        this.time = time;
        this.dateUrl = dateUrl;
        this.addTime = addTime;
        this.type = type;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDateUrl() {
        return dateUrl;
    }

    public void setDateUrl(String dateUrl) {
        this.dateUrl = dateUrl;
    }

    @Override
    public String toString() {
        return "MediaBean{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", time=" + time +
                ", dateUrl='" + dateUrl + '\'' +
                ", addTime='" + addTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
