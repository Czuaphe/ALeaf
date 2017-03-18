package com.czuaphe.aleaf.Utils;

import android.content.Context;

import com.czuaphe.aleaf.Bean.Album;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/18.
 */

public class AlbumManager {

    private ArrayList<Album> albums;

    public AlbumManager() {
        albums = new ArrayList<>();
    }

    public void loadAlbums(Context context) {
        albums = MediaStoreProvider.getAlbums(context);
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }



}
