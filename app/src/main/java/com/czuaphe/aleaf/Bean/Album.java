package com.czuaphe.aleaf.Bean;

import java.util.ArrayList;

/**
 * Created by Czuaphe on 2017/3/17.
 */

public class Album {

    private int id;  // MediaStore.Files.FileColums.PARENT

    private String title;  // MediaStore.Image.Media.BUCKET_DISPLAY_NAME

    private ArrayList<Media> medias;

    public Album() {

        medias = new ArrayList<>();
    }

    public Album(String title, int id, ArrayList<Media> medias) {
        this.title = title;
        this.id = id;
        this.medias = medias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Media> getMedias() {
        return medias;
    }

    public void setMedias(ArrayList<Media> medias) {
        this.medias = medias;
    }
}
