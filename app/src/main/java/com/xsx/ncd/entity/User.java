package com.xsx.ncd.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public class User {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String account;

    @DatabaseField(canBeNull = false)
    private String password;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean createqr;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean checkqr;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean adduser;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean deluser;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean edituser;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean upsoft;

    @DatabaseField(defaultValue = "false", columnDefinition = "bit(1)", canBeNull = false)
    private Boolean downsoft;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCreateqr() {
        return createqr;
    }

    public void setCreateqr(Boolean createqr) {
        this.createqr = createqr;
    }

    public Boolean getCheckqr() {
        return checkqr;
    }

    public void setCheckqr(Boolean checkqr) {
        this.checkqr = checkqr;
    }

    public Boolean getAdduser() {
        return adduser;
    }

    public void setAdduser(Boolean adduser) {
        this.adduser = adduser;
    }

    public Boolean getDeluser() {
        return deluser;
    }

    public void setDeluser(Boolean deluser) {
        this.deluser = deluser;
    }

    public Boolean getEdituser() {
        return edituser;
    }

    public void setEdituser(Boolean edituser) {
        this.edituser = edituser;
    }

    public Boolean getUpsoft() {
        return upsoft;
    }

    public void setUpsoft(Boolean upsoft) {
        this.upsoft = upsoft;
    }

    public Boolean getDownsoft() {
        return downsoft;
    }

    public void setDownsoft(Boolean downsoft) {
        this.downsoft = downsoft;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
