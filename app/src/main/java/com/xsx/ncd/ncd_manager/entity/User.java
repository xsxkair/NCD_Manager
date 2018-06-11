package com.xsx.ncd.ncd_manager.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public class User extends BaseEntity{

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String userid;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = true, defaultValue = "false", columnDefinition = "bit(1)")
    private Boolean men;

    @DatabaseField(canBeNull = true)
    private String age;

    @DatabaseField(canBeNull = true)
    private String phone;

    @DatabaseField(canBeNull = true)
    private String dep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMen() {
        return men;
    }

    public void setMen(Boolean men) {
        this.men = men;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public void resetUser(){
        this.id = null;
        this.name = null;
        this.userid = null;
        this.age = null;
        this.men = null;
        this.phone = null;
        this.dep = null;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", men=" + men +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}
