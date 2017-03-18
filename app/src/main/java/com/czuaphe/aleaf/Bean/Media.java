package com.czuaphe.aleaf.Bean;

/**
 * Created by Czuaphe on 2017/3/17.
 */

public class Media {

    private String path;   // MediaStore.Files.FileColumns.DATA(String) ：文件的路径

    private String mimeType;   // MediaStore.Files.FileColumns.MIME_TYPE：文件的后缀类型？

    Media() {

    }

    Media(String path, String mimeType) {
        this.path = path;
        this.mimeType = mimeType;
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
}
