package com.example.zd.mycontentprovider.annitioon;

import java.io.Serializable;

/**
 * use to do
 *
 * @author zhangdong on 2018/2/23 0023.
 * @version 1.0
 * @see .
 * @since 1.0
 */

public class ProgressBean implements Serializable {
    private int time;
    private String str;

    public ProgressBean(int time, String str) {
        this.time = time;
        this.str = str;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
