package com.wonggigi.entity;

/**
 * Created by handle on 17-1-17.
 */
public class Document {
    private String title;
    private String content;
    private String url;
    private int id;
    private Double bm25;
    private Double pr;
    private Double bm25Pr;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getBm25() {
        return bm25;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Double getBm25Pr() {
        return bm25Pr;
    }

    public Double getPr() {
        return pr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBm25(Double bm25) {
        this.bm25 = bm25;
    }

    public void setBm25Pr(Double bm25Pr) {
        this.bm25Pr = bm25Pr;
    }

    public void setPr(Double pr) {
        this.pr = pr;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
