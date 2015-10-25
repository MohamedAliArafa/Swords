package com.example.nezarsaleh.shareknitest;

/**
 * Created by Nezar Saleh on 10/25/2015.
 */
class Video {
    int id;
    String uri;
    String mimeType;
    String title;

    Video(int id, String uri, String mimeType, String title) {
        this.id=id;
        this.uri=uri;
        this.mimeType=mimeType;
        this.title=title;
    }

    @Override
    public String toString() {
        return(title);
    }
}