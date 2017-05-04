package com.czuaphe.aleaf.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.R;

import java.util.List;



public class ShowMediaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;

    private boolean fullScreenMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_media);

        imageView = (ImageView) findViewById(R.id.show_image);
        imageView.setOnClickListener(this);
        List<Album> list = getAlbumManager().getAlbums();

        Intent intent = getIntent();
        int albumId = intent.getIntExtra("albumId", 0);
        int mediaId = intent.getIntExtra("mediaId", 0);

        Glide.with(this).load(list.get(albumId).getMedias().get(mediaId).getPath())
                .into(imageView);

        hideSystemUI();

    }

    private void hideSystemUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
                fullScreenMode = true;
            }
        });
    }


    private void showSystemUI() {
        runOnUiThread(new Runnable() {
            public void run() {

                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                fullScreenMode = false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (fullScreenMode) {
            showSystemUI();
        }else {
            hideSystemUI();
        }
    }
}
