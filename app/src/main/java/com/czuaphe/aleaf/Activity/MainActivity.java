package com.czuaphe.aleaf.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.czuaphe.aleaf.Activity.BaseActivity;
import com.czuaphe.aleaf.Adapter.AlbumAdapter;
import com.czuaphe.aleaf.Adapter.MediaAdapter;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

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
                // 获得被点击的相册的序号
                int id = (int) v.findViewById(R.id.album_name).getTag();
                albumAdapter.notifyItemChanged(getAlbumManager().toggleAlbumSelected(id));

                // 如果selectedAlbum的大小为0，则退出editMode模式，并刷新菜单（现在还没有菜单）
                if(getAlbumManager().selectedAlbumCount() == 0) {
                    editMode = false;

                }
            } else {
                int albumNum = (int) v.findViewById(R.id.album_name).getTag();
                Album album = getAlbumManager().getAlbums().get(albumNum);
                getAlbumManager().setCurrentAlbumNum(albumNum);
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
            // 长按进入编辑模式
            editMode = true;
            // 长按相册时，将对应的Album的数据修改，然后Adapter更新数据
            int id = (int) v.findViewById(R.id.album_name).getTag();
            albumAdapter.notifyItemChanged(getAlbumManager().toggleAlbumSelected(id));
            // 最后更新菜单
            invalidateOptionsMenu();
            //Log.d(TAG, "onLongClick:" + getAlbumManager().getAlbums().get(id).getTitle());
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
                int mediaId = (int) v.findViewById(R.id.media_path).getTag();
                Intent intent = new Intent(MainActivity.this, ShowMediaActivity.class);
                intent.putExtra("mediaId", mediaId);
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
        toolbar.setNavigationIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_menu).sizeDp(18).color(Color.WHITE));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        menu.findItem(R.id.action_search).setIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_search).sizeDp(18).color(Color.WHITE));
        menu.findItem(R.id.action_sort).setIcon(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_sort).sizeDp(18).color(Color.WHITE));

        MenuItem action_sort = menu.findItem(R.id.action_sort);

        /*
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Come Soon!");
        */

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        menu.findItem(R.id.action_sort).setVisible(albumMode);
        menu.findItem(R.id.action_search).setVisible(albumMode);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.date_action_sort:
                item.setChecked(true);
                return true;
            case R.id.name_action_sort:
                item.setChecked(true);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

        private void toggleRecyclerVisibility() {

        rvAlbums.setVisibility(albumMode ? View.VISIBLE : View.GONE);
        rvMedias.setVisibility(albumMode ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onBackPressed() {


            if(editMode){
                // 然后退出编辑模式
                editMode = false;
                // 将对应选中的相册或媒体刷新
                getAlbumManager().loadAlbums(this);
                if(albumMode){
                    albumAdapter.DataSetChanged(getAlbumManager().getAlbums());
                } else {
                    int albumNum = getAlbumManager().getCurrentAlbumNum();
                    Album album = getAlbumManager().getAlbums().get(albumNum);
                    getAlbumManager().setCurrentAlbum(album);
                    mediaAdapter.DataSetChanged(getAlbumManager().getCurrentAlbum().getMedias());
                }
                // 刷新菜单
                invalidateOptionsMenu();

            }
            else {

                if(!albumMode) {
                    albumMode = true;
                    toggleRecyclerVisibility();
                    // 刷新一下AlbumAdapter中的数据
                    albumAdapter.DataSetChanged(getAlbumManager().getAlbums());
                    // 刷新菜单
                    invalidateOptionsMenu();
                } else {
                    super.onBackPressed();
                }
            }

    }

}
