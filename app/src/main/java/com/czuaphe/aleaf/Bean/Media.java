package com.czuaphe.aleaf.Bean;

import java.io.Serializable;

/**
 * Created by Czuaphe on 2017/3/17.
 */

public class Media implements Serializable {

    private String path = null;   // MediaStore.Files.FileColumns.DATA(String) ：文件的路径
    private long dateModified = 0;
    private long size = 0;
    private int orientation = 0;
    private String mimeType = null;   // MediaStore.Files.FileColumns.MIME_TYPE：文件的后缀类型

    private boolean selected = false;

    public Media() {}

    public Media(String path, String mimeType, long size, long dateModified, int orientation) {
        this.path = path;
        this.mimeType = mimeType;
        this.size = size;
        this.dateModified = dateModified;
        this.orientation = orientation;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
