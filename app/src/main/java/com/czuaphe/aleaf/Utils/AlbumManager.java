package com.czuaphe.aleaf.Utils;

import android.content.Context;
import android.util.Log;

import com.czuaphe.aleaf.Bean.Album;

import java.util.ArrayList;


/**
 * Created by admin on 2017/3/18.
 */

public class AlbumManager {

    private static final String TAG = "AlbumManager";

    private ArrayList<Album> albums;

    private Album currentAlbum;

    private ArrayList<Album> selectedAlbums;

    public AlbumManager() {
        albums = new ArrayList<>();
        selectedAlbums = new ArrayList<>();
        currentAlbum = new Album();
    }

    public void loadAlbums(Context context) {
        albums = MediaStoreProvider.getAlbums(context);
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public int selectedAlbumCount() {
        return selectedAlbums.size();
    }

    public int toggleAlbumSelected(int id) {

        Album album = albums.get(id);

        album.setSelected(!album.isSelected());

        if(album.isSelected()) {
            selectedAlbums.add(album);
            //Log.d(TAG, "toggleAlbumSelected: Add Album:" + album.getTitle());
        } else  {
            selectedAlbums.remove(album);
            //Log.d(TAG, "toggleAlbumSelected: Remote Album:" + album.getTitle());
        }

        return id;
    }

    public void setCurrentAlbum(Album album) {
        currentAlbum = album;
    }

    public Album getCurrentAlbum() {
        return currentAlbum;
    }

}
