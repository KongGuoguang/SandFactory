package com.fenjin.data.bean;

import com.fenjin.common.ToolUtil;

public class ChengZhongStatisticsItem {
    private float total;

    private float hesha;

    private float feisha;

    private float other;

    private String title;

    private int titleColor;

    private int backgroundColor;


    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getHesha() {
        return hesha;
    }

    public void setHesha(float hesha) {
        this.hesha = hesha;
    }

    public float getFeisha() {
        return feisha;
    }

    public void setFeisha(float feisha) {
        this.feisha = feisha;
    }

    public float getOther() {
        return other;
    }

    public void setOther(float other) {
        this.other = other;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTotalInt() {
        return ToolUtil.getInstance().subZeroAndDot(total);
    }

    public String getHeShaInt() {
        return ToolUtil.getInstance().subZeroAndDot(hesha);
    }

    public String getFeiShaInt() {
        return ToolUtil.getInstance().subZeroAndDot(feisha);
    }

    public String getOtherInt() {
        return ToolUtil.getInstance().subZeroAndDot(other);
    }
}
