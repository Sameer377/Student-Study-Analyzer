package com.page_1.app.DisplayFiles.Links;

public class LinksModel {
    private String urlName,url;

    public LinksModel(String urlName,String url) {
        this.urlName=urlName;
        this.url=url;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
