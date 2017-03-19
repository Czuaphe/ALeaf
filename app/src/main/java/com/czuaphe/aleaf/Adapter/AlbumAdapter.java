package com.czuaphe.aleaf.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.czuaphe.aleaf.Bean.Album;
import com.czuaphe.aleaf.R;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<Album> albums;
    private Context context;

    private View.OnClickListener onClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView preview;
        TextView title;
        TextView id;

        ViewHolder(View view) {
            super(view);
            preview = (ImageView) view.findViewById(R.id.album_preview);
            title = (TextView) view.findViewById(R.id.album_name);
            id = (TextView) view.findViewById(R.id.album_count);
        }

    }

    public AlbumAdapter(Context context, ArrayList<Album> data) {
        this.context = context;
        albums = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int p) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);

        view.setOnClickListener(onClickListener);

        return new ViewHolder(view);


    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.title.setText(album.getTitle());
        holder.id.setText(String.valueOf(album.getMedias().size()));
        holder.title.setTag(album);

        Glide.with(context).load(album.getMedias().get(0).getPath())
             .into(holder.preview);

    }

    public void setOnClickListener(View.OnClickListener lis) {
        onClickListener = lis;
    }

    public void DataSetChanged(ArrayList<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

}
