package com.czuaphe.aleaf.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czuaphe.aleaf.Adapter.ShowMediaPagerAdapter;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.Bean.Media;
import com.czuaphe.aleaf.R;

import java.util.List;



public class ShowMediaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;
    private ViewPager viewPager;

    private ShowMediaPagerAdapter adapter;


    private boolean fullScreenMode = true;


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_media);

//        imageView = (ImageView) findViewById(R.id.show_image);
//        imageView.setOnClickListener(this);
        List<Album> list = getAlbumManager().getAlbums();

        Intent intent = getIntent();
        int albumId = intent.getIntExtra("albumId", 0);
        int mediaId = intent.getIntExtra("mediaId", 0);

// start here
        List<Media> mediaList = list.get(albumId).getMedias();

        adapter = new ShowMediaPagerAdapter(getSupportFragmentManager(), mediaList);
        viewPager = (ViewPager) findViewById(R.id.show_image);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mediaId);
        viewPager.setPageTransformer(true, new DepthPageTransformer());

//        Glide.with(this).load(list.get(albumId).getMedias().get(mediaId).getPath()).into(imageView);

        hideSystemUI();

    }

    public void hideSystemUI() {
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


    public void showSystemUI() {
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
        toggleSystemUI();
    }

    public void toggleSystemUI() {
        if (fullScreenMode) {
            showSystemUI();
        }else {
            hideSystemUI();
        }
    }

}
