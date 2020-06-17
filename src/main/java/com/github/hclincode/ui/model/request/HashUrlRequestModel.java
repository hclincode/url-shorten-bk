package com.github.hclincode.ui.model.request;

import java.util.Optional;

public class HashUrlRequestModel {
    private String url;
    private Optional<String> urlHash = Optional.empty();

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlHash() {
        return this.urlHash.isPresent() ? this.urlHash.get() : "";
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = Optional.of(urlHash);
    }
}