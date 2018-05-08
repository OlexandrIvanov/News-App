package com.example.user.newsapp.Adapter;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.newsapp.View.MainActivity;
import com.example.user.newsapp.Model.ModelSources.Source;
import com.example.user.newsapp.Model.ModelTopNews.Article;
import com.example.user.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NoticeViewHolder> {

    private MainActivity mainActivity;
    private List<Article> dataList;
    private List<Source> dataListSources;
    private int whatNewsToDownload;



    public MyAdapter(List<Article> dataList, MainActivity mainActivity, int whatNewsToDownload) {
        this.dataList = dataList;
        this.mainActivity = mainActivity;
        this.whatNewsToDownload = whatNewsToDownload;
    }

    public MyAdapter(List<Source> dataListSources, MainActivity mainActivity) {
        this.dataListSources = dataListSources;
        this.mainActivity = mainActivity;

    }


    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_leyout, parent, false);

        return new NoticeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyAdapter.NoticeViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (whatNewsToDownload ==1) {
            holder.textViewSourceDescription.setText(dataList.get(position).getDescription());
            holder.textViewSourceName.setText(dataList.get(position).getSource().getName());

            String time = dataList.get(position).getPublishedAt();

            holder.textViewTime.setText(time.substring(0,10));
            String imageURL = dataList.get(position).getUrlToImage();

            Picasso.with(mainActivity).load(imageURL).into(holder.imageView);
        }else {
            holder.textViewSourceDescription.setText(dataListSources.get(position).getDescription());
            holder.textViewSourceName.setText(dataListSources.get(position).getName());
            holder.textViewTime.setText("");
        }

        holder.imageViewFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    holder.imageViewFavorites.setImageResource(R.drawable.btn_star_big_on);
                    if (whatNewsToDownload == 1) {

                        if (!dataList.get(position).getIfFavorites()) {
                            String time = dataList.get(position).getPublishedAt();
                            mainActivity.add_in_DB(dataList.get(position).getDescription(),
                                    dataList.get(position).getSource().getName(), time.substring(0, 10));

                            dataList.get(position).setIfFavorites(true);
                            Toast.makeText(mainActivity, R.string.toast_text, Toast.LENGTH_SHORT).show();

                        }else Toast.makeText(mainActivity, R.string.toast_text_already_added, Toast.LENGTH_SHORT).show();

                    } else {

                        if (!dataListSources.get(position).getIfFavorites()) {

                            mainActivity.add_in_DB(dataListSources.get(position).getDescription(),
                                    dataListSources.get(position).getName(), " ");

                            dataListSources.get(position).setIfFavorites(true);

                            Toast.makeText(mainActivity, R.string.toast_text, Toast.LENGTH_SHORT).show();
                        }else  Toast.makeText(mainActivity, R.string.toast_text_already_added, Toast.LENGTH_SHORT).show();
                    }

            }
        });

        holder.imageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whatNewsToDownload ==1) {
                    mainActivity.shareWithFacebook(dataList.get(position).getUrl());

                }else mainActivity.shareWithFacebook(dataListSources.get(position).getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (whatNewsToDownload ==1){
            return dataList.size();
        }else return dataListSources.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSourceName,textViewSourceDescription,textViewTime;

        ImageView imageView, imageViewFavorites, imageViewFacebook;

        public NoticeViewHolder(View itemView) {
            super(itemView);

            textViewSourceName = (TextView)itemView.findViewById(R.id.textViewSourceName);
            textViewSourceDescription = (TextView)itemView.findViewById(R.id.textViewSourceDescription2);
            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageViewFavorites = (ImageView) itemView.findViewById(R.id.imageViewFavorites);
            imageViewFacebook = (ImageView) itemView.findViewById(R.id.imageViewFacebook);

        }
    }
}
