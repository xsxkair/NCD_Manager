package com.xsx.ncd.ncd_manager.entity;

public class SettingIco {
    private int iId;
    private String iName;

    public SettingIco() {
    }

    public SettingIco(int iId, String iName) {
        this.iId = iId;
        this.iName = iName;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
