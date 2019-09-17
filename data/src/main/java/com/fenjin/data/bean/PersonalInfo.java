package com.fenjin.data.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;


public class PersonalInfo extends BaseObservable {

    private int id;

    private String name;

    private String username;

    private String mobileTel;

    private String headImg;

    private int sex; // 1-男，2-女

    private String address;

    private String company;

    private List<Long> roles;

    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.fenjin.data.BR.name);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(com.fenjin.data.BR.username);
    }

    @Bindable
    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
        notifyPropertyChanged(com.fenjin.data.BR.mobileTel);
    }

    @Bindable
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
        notifyPropertyChanged(com.fenjin.data.BR.headImg);
    }

    @Bindable
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
        notifyPropertyChanged(com.fenjin.data.BR.sex);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(com.fenjin.data.BR.address);
    }

    @Bindable
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
        notifyPropertyChanged(com.fenjin.data.BR.company);
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
