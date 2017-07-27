package com.czuaphe.aleaf.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private View.OnLongClickListener onLongClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        ImageView preview;
        ImageView selectedIcon;
        LinearLayout message;
        TextView title;
        TextView number;
        TextView word;

        ViewHolder(View view) {
            super(view);
            layout =  view.findViewById(R.id.header_album_card);
            preview = (ImageView) view.findViewById(R.id.album_preview);
            title = (TextView) view.findViewById(R.id.album_name);
            selectedIcon = (ImageView) view.findViewById(R.id.selected_icon);
            number = (TextView) view.findViewById(R.id.album_count);
            message = (LinearLayout) view.findViewById(R.id.album_message);
            word = (TextView) view.findViewById(R.id.album_word);
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
        view.setOnLongClickListener(onLongClickListener);

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
        holder.number.setText(String.valueOf(album.getMedias().size()));
        holder.title.setTag(position);

        Glide.with(context).load(album.getMedias().get(0).getPath())
             .into(holder.preview);


        if(album.isSelected()) {
            // 如果相册被选中，显示半透明背景和选中的图标
            holder.preview.setColorFilter(0x88000000, PorterDuff.Mode.SRC_ATOP);
            holder.selectedIcon.setVisibility(View.VISIBLE);
            // 如果相册被选中，将文字背景变色
            holder.message.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.title.setTextColor(Color.WHITE);
            holder.word.setTextColor(Color.WHITE);
        } else {
            holder.preview.clearColorFilter();
            holder.selectedIcon.setVisibility(View.GONE);
            holder.message.setBackgroundColor(Color.WHITE);
            holder.title.setTextColor(Color.BLACK);
            holder.word.setTextColor(Color.GRAY);
        }


    }

    public void setOnClickListener(View.OnClickListener lis) {
        onClickListener = lis;
    }

    public void setOnLongClickListener(View.OnLongClickListener lis) {
        this.onLongClickListener = lis;
    }

    public void setItemSelected(int id) {
        albums.get(id).setSelected(true);
        notify();
    }

    public void DataSetChanged(ArrayList<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

}
