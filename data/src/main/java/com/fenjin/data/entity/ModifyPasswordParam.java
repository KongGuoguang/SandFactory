package com.fenjin.data.entity;

/**
 * Author:kongguoguang
 * Date:2018-08-25
 * Time:16:21
 * Summary:
 */
public class ModifyPasswordParam {

    private String oldPwd;

    private String newPwd;

    public ModifyPasswordParam(String oldPwd, String newPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
