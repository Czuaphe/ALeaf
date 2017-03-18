package com.czuaphe.aleaf.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.czuaphe.aleaf.Activity.BaseActivity;
import com.czuaphe.aleaf.Activity.MainActivity;
import com.czuaphe.aleaf.R;

/**
 * Created by admin on 2017/3/18.
 */

public class SplashScreen extends BaseActivity {
    // 加载数据

    @Override
    public void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_splash);


        // 使用本地方法 获取读外置存储的权限

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        } else {

            loadAlbums();

        }


    }

    private void loadAlbums() {
        getAlbumManager().loadAlbums(getApplicationContext());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    loadAlbums();

                } else {
                    Toast.makeText(this, "You are denied the permission!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


}
