package com.fenjin.data.entity;

import java.util.List;

public class GetAllChannelResult {

    private EasyDarwin EasyDarwin;

    public EasyDarwin getEasyDarwin() {
        return EasyDarwin;
    }

    public void setEasyDarwin(EasyDarwin easyDarwin) {
        EasyDarwin = easyDarwin;
    }

    public class EasyDarwin{
        private Body Body;

        public Body getBody() {
            return Body;
        }

        public void setBody(Body body) {
            Body = body;
        }

        @Override
        public String toString() {
            return "EasyDarwin{" +
                    "Body=" + Body +
                    '}';
        }
    }

    public class Body{
        private String ChannelCount;

        private List<Channel> Channels;

        public String getChannelCount() {
            return ChannelCount;
        }

        public void setChannelCount(String channelCount) {
            ChannelCount = channelCount;
        }

        public List<Channel> getChannels() {
            return Channels;
        }

        public void setChannels(List<Channel> channels) {
            Channels = channels;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "ChannelCount='" + ChannelCount + '\'' +
                    ", Channels=" + Channels +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetAllChannelResult{" +
                "EasyDarwin=" + EasyDarwin +
                '}';
    }
}
