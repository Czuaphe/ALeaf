package com.czuaphe.aleaf.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.czuaphe.aleaf.Activity.ShowMediaActivity;
import com.czuaphe.aleaf.Bean.Media;

/**
 * Created by admin on 2017/5/9.
 */

public class ImageFragment extends Fragment {

    private Media image;

    public static ImageFragment getInstance(Media media) {

        ImageFragment imageFragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putSerializable("image", media);
        imageFragment.setArguments(args);

        return imageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);

        image = (Media) getArguments().getSerializable("image");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getContext());
        Glide.with(this)
                .load(image.getPath())
                .asBitmap()
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShowMediaActivity) getActivity()).toggleSystemUI();
            }
        });

        return imageView;
    }
}
