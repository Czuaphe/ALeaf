package com.czuaphe.aleaf.Bean;

import java.util.ArrayList;

/**
 * Created by Czuaphe on 2017/3/17.
 */

public class Album {

    private int id;  // MediaStore.Files.FileColums.PARENT

    private String title;  // MediaStore.Image.Media.BUCKET_DISPLAY_NAME

    private ArrayList<Media> medias = new ArrayList<>(); // 本相册下所有的媒体

    private boolean selected = false;

    private ArrayList<Media> selectedMedias = new ArrayList<>();  // 本相册下被选中的媒体

    public Album() {}

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<Media> getSelectedMedias() {
        return selectedMedias;
    }

    public void setSelectedMedias(ArrayList<Media> selectedMedias) {
        this.selectedMedias = selectedMedias;
    }

    public int toggleMediaSelected(int id) {
        Media media = medias.get(id);
        media.setSelected(!media.isSelected());

        if(media.isSelected()) {
            selectedMedias.add(media);
        } else {
            selectedMedias.remove(media);
        }
        return id;
    }

}
