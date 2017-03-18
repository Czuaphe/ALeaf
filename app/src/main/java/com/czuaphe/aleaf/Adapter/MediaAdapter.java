package com.czuaphe.aleaf.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.Bean.Media;
import com.czuaphe.aleaf.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/18.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private ArrayList<Media> medias;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView path;

        ViewHolder(View view) {
            super(view);

            path = (TextView)view.findViewById(R.id.media_path);
        }

    }

    public MediaAdapter() {
        medias = new ArrayList<>();
    }

    public MediaAdapter(ArrayList<Media> data) {
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
    }

    public void DataSetChanged(ArrayList<Media> medias) {
        this.medias = medias;
        notifyDataSetChanged();
    }


}
