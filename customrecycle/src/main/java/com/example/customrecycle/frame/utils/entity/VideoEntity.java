package com.example.customrecycle.frame.utils.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by U
 * <p>
 * on 2017/8/30
 * <p>
 * QQ:1347414707
 * <p>
 * For:
 */
@Entity
public class VideoEntity{

    @Property
    private String uri;//地址
    @Property
    private String name;//名字
    @Property
    private Long currentPosition;//当前进度
    @Generated(hash = 887633519)
    public VideoEntity(String uri, String name, Long currentPosition) {
        this.uri = uri;
        this.name = name;
        this.currentPosition = currentPosition;
    }
    @Generated(hash = 1984976152)
    public VideoEntity() {
    }
    public String getUri() {
        return this.uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getCurrentPosition() {
        return this.currentPosition;
    }
    public void setCurrentPosition(Long currentPosition) {
        this.currentPosition = currentPosition;
    }

}