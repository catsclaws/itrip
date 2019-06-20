package com.itrip.vo;

import java.io.Serializable;

public class TokenVO implements Serializable {
    public TokenVO() {
    }

    public TokenVO(String token, long genTime, long expTime) {
        this.token = token;
        this.genTime = genTime;
        this.expTime = expTime;
    }

    private String token;
    private long genTime;
    private long expTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getGenTime() {
        return genTime;
    }

    public void setGenTime(long genTime) {
        this.genTime = genTime;
    }

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }
}
