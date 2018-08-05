package com.fenjin.data.entity;


public class GetChannelResult {

    private EasyDarwin EasyDarwin;

    public EasyDarwin getEasyDarwin() {
        return EasyDarwin;
    }

    public void setEasyDarwin(EasyDarwin easyDarwin) {
        EasyDarwin = easyDarwin;
    }

    @Override
    public String toString() {
        return "GetChannelResult{" +
                "EasyDarwin=" + EasyDarwin +
                '}';
    }

    public class EasyDarwin{
        private Body Body;

        private Header Header;

        public Body getBody() {
            return Body;
        }

        public void setBody(Body body) {
            Body = body;
        }

        public GetChannelResult.Header getHeader() {
            return Header;
        }

        public void setHeader(GetChannelResult.Header header) {
            Header = header;
        }

        @Override
        public String toString() {
            return "EasyDarwin{" +
                    "Body=" + Body +
                    ", Header=" + Header +
                    '}';
        }
    }

    public class Body{
        private String ChannelName;

        private String URL;

        public String getChannelName() {
            return ChannelName;
        }

        public void setChannelName(String channelName) {
            ChannelName = channelName;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "ChannelName='" + ChannelName + '\'' +
                    ", URL='" + URL + '\'' +
                    '}';
        }
    }

    public class Header{
        private String CSeq;

        private String ErrorNum;

        private String ErrorString;

        private String MessageType;

        private String Version;

        public String getCSeq() {
            return CSeq;
        }

        public void setCSeq(String CSeq) {
            this.CSeq = CSeq;
        }

        public String getErrorNum() {
            return ErrorNum;
        }

        public void setErrorNum(String errorNum) {
            ErrorNum = errorNum;
        }

        public String getErrorString() {
            return ErrorString;
        }

        public void setErrorString(String errorString) {
            ErrorString = errorString;
        }

        public String getMessageType() {
            return MessageType;
        }

        public void setMessageType(String messageType) {
            MessageType = messageType;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version = version;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "CSeq='" + CSeq + '\'' +
                    ", ErrorNum='" + ErrorNum + '\'' +
                    ", ErrorString='" + ErrorString + '\'' +
                    ", MessageType='" + MessageType + '\'' +
                    ", Version='" + Version + '\'' +
                    '}';
        }
    }
}
