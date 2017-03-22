package com.czuaphe.aleaf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.Bean.Media;
import com.czuaphe.aleaf.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/18.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private ArrayList<Media> medias;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView preview;
        TextView path;

        ViewHolder(View view) {
            super(view);
            preview = (ImageView) view.findViewById(R.id.media_preview);
            path = (TextView)view.findViewById(R.id.media_path);
        }

    }

    public MediaAdapter(Context context) {
        this.context = context;
        medias = new ArrayList<>();
    }

    public MediaAdapter(Context context, ArrayList<Media> data) {
        this.context = context;
        medias = data;
    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int p) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        return new MediaAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    @Override
    public void onBindViewHolder(MediaAdapter.ViewHolder holder, int position) {
        Media media = medias.get(position);
        holder.path.setText(media.getPath());
        // 将图片名字隐藏
        holder.path.setVisibility(View.GONE);
        Glide.with(context).load(media.getPath()).into(holder.preview);
    }

    public void DataSetChanged(ArrayList<Media> medias) {
        this.medias = medias;
        notifyDataSetChanged();
    }


}
