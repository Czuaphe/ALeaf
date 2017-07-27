package com.czuaphe.aleaf.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.util.Measure;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czuaphe.aleaf.Adapter.ShowMediaPagerAdapter;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.Bean.Media;
import com.czuaphe.aleaf.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.List;



public class ShowMediaActivity extends BaseActivity implements View.OnClickListener {

//    private ImageView imageView;
    private ViewPager viewPager;
    private Toolbar toolbar;

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

        Intent intent = getIntent();
        int mediaId = intent.getIntExtra("mediaId", 0);

// start here
        List<Media> mediaList = getAlbumManager().getCurrentAlbum().getMedias();

        adapter = new ShowMediaPagerAdapter(getSupportFragmentManager(), mediaList);
        viewPager = (ViewPager) findViewById(R.id.show_image);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mediaId);
        viewPager.setPageTransformer(true, new DepthPageTransformer());

//        Glide.with(this).load(list.get(albumId).getMedias().get(mediaId).getPath()).into(imageView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_menu).sizeDp(18).color(Color.WHITE));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.bringToFront();

        toggleSystemUI();

    }

    public void hideSystemUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator())
                        .setDuration(200).start();
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
                toolbar.animate().translationY(getStatusBarHeight(getResources())).setInterpolator(new DecelerateInterpolator())
                        .setDuration(240).start();
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

    public static int getStatusBarHeight(Resources r) {
        int resourceId = r.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            return r.getDimensionPixelSize(resourceId);

        return 0;
    }

}
