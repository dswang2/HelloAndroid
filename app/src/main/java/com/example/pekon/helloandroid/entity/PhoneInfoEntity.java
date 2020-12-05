package com.example.pekon.helloandroid.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * create by dsw on 2020-11-20
 **/
@Entity
public class PhoneInfoEntity {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 1935607459)
    public PhoneInfoEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 519516684)
    public PhoneInfoEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
