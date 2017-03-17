package com.czuaphe.aleaf.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by Czuaphe on 2017/3/17.
 */

public class MediaStoreProvider {

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

}
