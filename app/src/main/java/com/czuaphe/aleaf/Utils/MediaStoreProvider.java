package com.czuaphe.aleaf.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.Bean.Media;

import java.util.ArrayList;

/**
 * Created by Czuaphe on 2017/3/17.
 */

public class MediaStoreProvider {

    private static final String TAG = "MediaStoreProvider";


    public static ArrayList<Album> getAlbums(Context context) {
        ArrayList<Album> dataList = new ArrayList<>();

        String[] projection = new String[]{MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Files.FileColumns.PARENT};
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "= ?" +  " ) GROUP BY ( " + MediaStore.Files.FileColumns.PARENT + " ";
        String[] selectionArgs = new String[] {String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) };
        Cursor cur = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), projection, selection, selectionArgs, null);
        if (cur != null && cur.moveToFirst()) {
            do {

                String title = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                int id = cur.getInt(cur.getColumnIndex(MediaStore.Files.FileColumns.PARENT));
                ArrayList<Media> medias = getAlbum(context, id);

                Album album = new Album(title, id, medias);
                dataList.add(album);

            } while (cur.moveToNext());
            cur.close();
        }
        return dataList;
    }


    public static ArrayList<Media> getAlbum(Context context, int albumId) {
        ArrayList<Media> mediasList = new ArrayList<>();


        String[] projection = new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.ORIENTATION
        };

        Uri images = MediaStore.Files.getContentUri("external");
        String selection, selectionArgs[];

        selection = "( "+MediaStore.Files.FileColumns.MEDIA_TYPE + "= ? ) and " + MediaStore.Files.FileColumns.PARENT + "=?";

        selectionArgs = new String[] {
                    String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                    String.valueOf(albumId)
        };

        Cursor cur = context.getContentResolver().query(
                images, projection, selection, selectionArgs,
                " " + MediaStore.Images.Media.DATE_TAKEN);

        if (cur != null) {
            if (cur.moveToFirst()) {
                do {

                    String path = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.DATA));
                    long size = cur.getLong(cur.getColumnIndex(MediaStore.Images.Media.SIZE));
                    int orientation = cur.getInt(cur.getColumnIndex(MediaStore.Images.Media.ORIENTATION));
                    String mimeType = cur.getString(cur.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                    long dateModified = cur.getLong(cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

                    Media media = new Media(path, mimeType, size, dateModified, orientation);

                    mediasList.add(media);
                }
                while (cur.moveToNext());
                cur.close();
            }
        }

        return mediasList;

    }

    // TODO: 2017/3/18 will get Album Thumbnails
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
