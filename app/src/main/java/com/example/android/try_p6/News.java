package com.example.android.try_p6;

public class News {

    private String sectionName;
    private String webTitle;
    private String url;
    private String webPublicationDate;
    private String time;
    String author;

    public News(String sectionName, String webTitle, String url, String webPublicationDate, String Time){
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.url = url;
        this.webPublicationDate = webPublicationDate;
        this.time = Time;
    }

    public News(String sectionName, String webTitle, String url, String webPublicationDate, String Time, String author){
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.url = url;
        this.webPublicationDate = webPublicationDate;
        this.time = Time;
        this.author = author;
    }

    public String getSectionName() {
        return sectionName;
    }
    public String getTime() {
        return time;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getUrl() {
        return url;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getAuthor(){
        return author;
    }
}
