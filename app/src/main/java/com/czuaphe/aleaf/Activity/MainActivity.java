package com.czuaphe.aleaf.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
    private boolean editMode = false;

    private View.OnClickListener albumClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(editMode) {
                int id = (int) v.findViewById(R.id.album_name).getTag();
                albumAdapter.notifyItemChanged(getAlbumManager().toggleAlbumSelected(id));
                // 如果selectedAlbum的大小为0，则退出editMode模式，并刷新菜单
                if(getAlbumManager().selectedAlbumCount() == 0) {
                    editMode = false;
                }
            } else {
                int albumNum = (int) v.findViewById(R.id.album_name).getTag();
                Album album = getAlbumManager().getAlbums().get(albumNum);
                getAlbumManager().setCurrentAlbum(album);
                albumMode = false;
                toggleRecyclerVisibility();
                mediaAdapter.DataSetChanged(album.getMedias());
            }
        }
    };

    private View.OnLongClickListener albumLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // 长按发现时，将对应的Album的数据修改就行，Adapter只负责显示控件
            int id = (int) v.findViewById(R.id.album_name).getTag();
            albumAdapter.notifyItemChanged(getAlbumManager().toggleAlbumSelected(id));
            //Log.d(TAG, "onLongClick:" + getAlbumManager().getAlbums().get(id).getTitle());
            editMode = true;
            return true;
        }
    };

    private View.OnClickListener mediaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(editMode) {
                int id = (int) v.findViewById(R.id.media_path).getTag();
                mediaAdapter.notifyItemChanged(getAlbumManager().getCurrentAlbum().toggleMediaSelected(id));
                // 如果selectedMedias的大小为0，则退出editMode模式，并刷新菜单
                if(getAlbumManager().getCurrentAlbum().getSelectedMedias().size() == 0) {
                    editMode = false;
                }
            } else {
                int mediaNum = (int) v.findViewById(R.id.media_path).getTag();
                Intent intent = new Intent(MainActivity.this, ShowMediaActivity.class);
                intent.putExtra("mediaId", mediaNum);
                startActivity(intent);
            }
        }
    };

    private View.OnLongClickListener mediaLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int id = (int) v.findViewById(R.id.media_path).getTag();
            mediaAdapter.notifyItemChanged(getAlbumManager().getCurrentAlbum().toggleMediaSelected(id));
            editMode = true;
            return true;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        /* 测试getAlbumManager()中的方法
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
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvAlbums = (RecyclerView) findViewById(R.id.rv_album);
        rvMedias = (RecyclerView) findViewById(R.id.rv_media);

        // setting Albums
        StaggeredGridLayoutManager albumLayout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager albumLayout = new LinearLayoutManager(this);
        albumAdapter = new AlbumAdapter(this, getAlbumManager().getAlbums());
        albumAdapter.setOnClickListener(albumClickListener);
        albumAdapter.setOnLongClickListener(albumLongClickListener);
        rvAlbums.setLayoutManager(albumLayout);
        rvAlbums.setAdapter(albumAdapter);

        // setting Medias
        //LinearLayoutManager mediaLayout = new LinearLayoutManager(this);
        StaggeredGridLayoutManager mediaLayout = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mediaAdapter = new MediaAdapter(this);
        mediaAdapter.setOnClickListener(mediaClickListener);
        mediaAdapter.setOnLongClickListener(mediaLongClickListener);
        rvMedias.setLayoutManager(mediaLayout);
        rvMedias.setAdapter(mediaAdapter);


        toggleRecyclerVisibility();

        Log.d(TAG, "onCreate: finished");

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
