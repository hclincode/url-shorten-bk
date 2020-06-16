package com.github.hclincode.shared.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UrlHashDto implements Serializable {
    private static final long serialVersionUID = 8660573128055421654L;
    private long id; // stored in DB
    private String url;
    private String urlHash;
    private Timestamp lastUpdateTimestamp;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlHash() {
        return this.urlHash;
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = urlHash;
    }

    public Timestamp getLastUpdateTimestamp() {
        return this.lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Timestamp lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }
}