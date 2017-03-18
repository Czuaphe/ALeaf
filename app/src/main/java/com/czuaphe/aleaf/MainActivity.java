package com.czuaphe.aleaf;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.czuaphe.aleaf.Adapter.AlbumAdapter;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.Utils.MediaStoreProvider;
import com.czuaphe.aleaf.Utils.PermissionUtils;

import java.io.File;
import java.io.LineNumberInputStream;
import java.util.ArrayList;

import static com.czuaphe.aleaf.Utils.MediaStoreProvider.getAlbums;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView rvAlbums;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvAlbums = (RecyclerView) findViewById(R.id.rv_album);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rvAlbums.setLayoutManager(layout);


        ArrayList<Album> albums = getAlbumManager().getAlbums();

        for (int i = 0; i < albums.size(); i ++) {
            Log.d(TAG, "onCreate: " + albums.get(i).getTitle());
            Log.d(TAG, "onCreate: " + albums.get(i).getId());
        }

        AlbumAdapter albumAdapter = new AlbumAdapter(albums);
        rvAlbums.setAdapter(albumAdapter);



        // 使用本地方法 获取读外置存储的权限
        /*
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            data = getAlbums(this);
        }
        */


        ;


        //list = (ListView) findViewById(R.id.listView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, getAlbumsData());
        //list.setAdapter(adapter);

        //测试中
        //getThumbnails(this);


    }


    public ArrayList<String> getAlbumsData() {

        // 使用工具类 PermissionUtis 获取读外置存储的权限
        if (PermissionUtils.checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {


            return getAlbums(this);


        } else {

            // 请求权限
            PermissionUtils.requestPermissions(MainActivity.this, 1, Manifest.permission.READ_EXTERNAL_STORAGE);
            return defaultAlbum();
        }

    }

    public ArrayList<String> defaultAlbum() {
        ArrayList<String> data = new ArrayList<>();
        data.add("empty!!");
        return data;

    }



    /*
    public static ArrayList<String> getAlbums(Context context) {

        ArrayList<String> dataList = new ArrayList<>();

        String[] projection = new String[]{MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Files.FileColumns.PARENT};
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "= ?" +  " ) GROUP BY ( " + MediaStore.Files.FileColumns.PARENT + " ";
        String[] selectionArgs = new String[] {String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) };
        Cursor cur = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, null);
        if (cur != null && cur.moveToFirst()) {
            do {

                dataList.add(cur.getString(0) + "\n" + cur.getString(1));

                //Log.d(TAG, "getHiddenAlbums: " + cur.getString(0));
                //Log.d(TAG, "getHiddenAlbums: " + cur.getString(1));
            } while (cur.moveToNext());
            cur.close();
        }
        return dataList;
    }
    */

    public ArrayList<String> getThumbnails(Context context) {
        ArrayList<String> list = new ArrayList<>();

        String[] projection = new String[] { MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.KIND, MediaStore.Images.Thumbnails.DATA};
        String selection = null;
        Cursor cur = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);

        if(cur != null && cur.moveToFirst()) {

            do {

                String tn_ImageId = cur.getString(cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID));
                int tn_Kind = cur.getInt(cur.getColumnIndex(MediaStore.Images.Thumbnails.KIND));
                String tn_Data = cur.getString(cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA));

                Log.d(TAG, "getThumbnails: ImageId: " + tn_ImageId);
                Log.d(TAG, "getThumbnails: Kind: " + tn_Kind);
                Log.d(TAG, "getThumbnails: Data: " + tn_Data);

            } while (cur.moveToNext());

            cur.close();

        }

        return list;

    }


}
