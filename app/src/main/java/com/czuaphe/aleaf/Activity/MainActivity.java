package com.czuaphe.aleaf.Activity;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.czuaphe.aleaf.Activity.BaseActivity;
import com.czuaphe.aleaf.Adapter.AlbumAdapter;
import com.czuaphe.aleaf.Adapter.MediaAdapter;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView rvAlbums;
    private AlbumAdapter albumAdapter;

    private RecyclerView rvMedias;
    private MediaAdapter mediaAdapter;



    private boolean albumMode = true;


    private View.OnClickListener albumClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Album album = (Album) v.findViewById(R.id.album_name).getTag();

            albumMode = false;
            toggleRecyclerVisibility();
            mediaAdapter.DataSetChanged(album.getMedias());
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        ArrayList<Album> albums = getAlbumManager().getAlbums();

        for (int i = 0; i < albums.size(); i ++) {

            for(int j = 0; j < albums.get(i).getMedias().size(); j ++) {
                Log.d(TAG, "onCreate: " + albums.get(i).getTitle() + albums.get(i).getMedias().get(j).getPath());
                Log.d(TAG, "onCreate: " + albums.get(i).getTitle() + albums.get(i).getMedias().get(j).getMimeType());
                Log.d(TAG, "onCreate: " + albums.get(i).getTitle() + albums.get(i).getMedias().get(j).getSize());
                Log.d(TAG, "onCreate: " + albums.get(i).getTitle() + albums.get(i).getMedias().get(j).getDateModified());
                Log.d(TAG, "onCreate: " + albums.get(i).getTitle() + albums.get(i).getMedias().get(j).getOrientation());
            }
        }



        rvAlbums = (RecyclerView) findViewById(R.id.rv_album);
        rvMedias = (RecyclerView) findViewById(R.id.rv_media);

        // setting Albums
        StaggeredGridLayoutManager albumLayout = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager albumLayout = new LinearLayoutManager(this);
        albumAdapter = new AlbumAdapter(getAlbumManager().getAlbums());
        albumAdapter.setOnClickListener(albumClickListener);
        rvAlbums.setLayoutManager(albumLayout);
        rvAlbums.setAdapter(albumAdapter);

        // setting Medias
        LinearLayoutManager mediaLayout = new LinearLayoutManager(this);

        mediaAdapter = new MediaAdapter();
        rvMedias.setLayoutManager(mediaLayout);
        rvMedias.setAdapter(mediaAdapter);


        toggleRecyclerVisibility();



        // 使用本地方法 获取读外置存储的权限
        /*
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            data = getAlbums(this);
        }
        */


        //测试中
        //getThumbnails(this);


    }


    private void toggleRecyclerVisibility() {

        rvAlbums.setVisibility(albumMode ? View.VISIBLE : View.GONE);
        rvMedias.setVisibility(albumMode ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if(!albumMode) {
            albumMode = true;
            toggleRecyclerVisibility();
            // 刷新一下AlbumAdapter中的数据
            albumAdapter.DataSetChanged(getAlbumManager().getAlbums());
        } else {
            super.onBackPressed();
        }
    }

}
