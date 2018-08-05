package com.fenjin.data.entity;

public class Channel {

    private int Channel;

    private String Name;

    private int Online;

    private String SnapURL;

    public int getChannel() {
        return Channel;
    }

    public void setChannel(int channel) {
        Channel = channel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getOnline() {
        return Online;
    }

    public void setOnline(int online) {
        Online = online;
    }

    public String getSnapURL() {
        return SnapURL;
    }

    public void setSnapURL(String snapURL) {
        SnapURL = snapURL;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "Channel=" + Channel +
                ", Name='" + Name + '\'' +
                ", Online=" + Online +
                ", SnapURL='" + SnapURL + '\'' +
                '}';
    }
}
