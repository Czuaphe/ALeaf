package com.czuaphe.aleaf;

import android.app.Application;

import com.czuaphe.aleaf.Utils.AlbumManager;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/18.
 */

public class MyApplication extends Application {

    private AlbumManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = new AlbumManager();

    }

    public AlbumManager getAlbumManager() {
        return manager;
    }

}
