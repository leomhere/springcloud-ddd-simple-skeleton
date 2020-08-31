package com.mhere.common.transfer;

import java.util.Date;

public class TokenTransfer {

    private String token;
    private Date expiredAt;
    private String refreshKey;
    private Date refreshExpiredAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getRefreshKey() {
        return refreshKey;
    }

    public void setRefreshKey(String refreshKey) {
        this.refreshKey = refreshKey;
    }

    public Date getRefreshExpiredAt() {
        return refreshExpiredAt;
    }

    public void setRefreshExpiredAt(Date refreshExpiredAt) {
        this.refreshExpiredAt = refreshExpiredAt;
    }
}
