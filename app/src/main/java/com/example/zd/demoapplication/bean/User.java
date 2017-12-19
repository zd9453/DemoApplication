package com.example.zd.demoapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by zhangdong on 2017/12/11 0011.
 */

public class User implements Parcelable {
    private String name;
    private int age;

    public User() {
    }

    protected User(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
