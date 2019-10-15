package com.example.processesdemo.cityselecter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.processesdemo.R;

import me.yokeyword.indexablerv.IndexableAdapter;

public class SelecterCityAdapter extends IndexableAdapter<UserEntity> {
    private LayoutInflater mInflater;

    public SelecterCityAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv_index.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, UserEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tv_name.setText(entity.getNick());
        vh.tv_mobile.setText(entity.getMobile());
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_index;

        public IndexVH(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv_index = (TextView) rootView.findViewById(R.id.tv_index);
        }

    }

    private class ContentVH extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView img_avatar;
        public TextView tv_name;
        public TextView tv_mobile;

        public ContentVH(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.img_avatar = (ImageView) rootView.findViewById(R.id.img_avatar);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_mobile = (TextView) rootView.findViewById(R.id.tv_mobile);
        }

    }
}
