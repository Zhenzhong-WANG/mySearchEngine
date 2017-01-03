package com.wonggigi.entity;

import java.util.ArrayList;

/**
 * Created by Hanoi on 2016/12/14.
 */
public class Document {

    private String url;
    private String title;
    private String path;
    private String content;
    private int id;
    private ArrayList<String> link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ArrayList<String> getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLink(ArrayList<String> link) {
        this.link = link;
    }
}
