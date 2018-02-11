package com.example.zd.mycontentprovider.annitioon;

/**
 * use to do
 *
 * @author zhangdong on 2018/2/8 0008.
 * @version 1.0
 * @see .
 * @since 1.0
 */

public class FBean {

    @TestAnnotation(tag = 9999)
    int age;

    String name;

    private String sex;

    int have;

    public FBean() {
    }

    public FBean(int age, String name, String sex, int have) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.have = have;
    }

    private int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = have;
    }

    @Override
    public String toString() {
        return "FBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
