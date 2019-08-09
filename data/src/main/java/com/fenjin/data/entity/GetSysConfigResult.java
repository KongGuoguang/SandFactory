package com.fenjin.data.entity;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:17:39
 * Summary:
 */
public class GetSysConfigResult {

    private int flag;

    private List<Config> result;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<Config> getResult() {
        return result;
    }

    public void setResult(List<Config> result) {
        this.result = result;
    }

    public class Config {
        private int id;

        private String key;

        private String value;

        private String info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
