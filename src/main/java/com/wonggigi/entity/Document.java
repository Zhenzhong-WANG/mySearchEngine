package com.wonggigi.entity;

/**
 * Created by Hanoi on 2016/12/14.
 */
public class Document {

    private String url;
    private String title;
    private String path;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
