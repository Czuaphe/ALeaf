package com.czuaphe.aleaf.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.czuaphe.aleaf.MyApplication;
import com.czuaphe.aleaf.Utils.AlbumManager;

/**
 * Created by admin on 2017/3/18.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AlbumManager getAlbumManager() {
        return ((MyApplication) getApplicationContext()).getAlbumManager();
    }

}
