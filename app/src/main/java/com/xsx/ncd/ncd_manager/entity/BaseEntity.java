package com.xsx.ncd.ncd_manager.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    private Long index;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
