package com.example.customrecycle.frame.utils.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by U
 * <p>
 * on 2017/8/30
 * <p>
 * QQ:1347414707
 * <p>
 * For:
 */
public class VideoEntity implements Serializable {
    @Expose
    private String uri;//地址

    @Expose
    private String name;//名字
    @Expose
    private Long currentPosition;//当前进度

    public VideoEntity(String uri, String name, Long currentPosition) {
        this.uri = uri;
        this.name = name;
        this.currentPosition = currentPosition;
    }

    public VideoEntity() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Long currentPosition) {
        this.currentPosition = currentPosition;
    }
}