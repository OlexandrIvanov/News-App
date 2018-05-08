package com.example.user.newsapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.newsapp.Database.MyNews;
import com.example.user.newsapp.R;

import io.realm.RealmResults;

public class MyFavoritesAdapter extends RecyclerView.Adapter<MyFavoritesAdapter.ViewHolder> {
    private final RealmResults<MyNews> myNews;

    public MyFavoritesAdapter(RealmResults<MyNews> books) {
        myNews = books;
    }


    @Override
    public MyFavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favorites_recycler_view_leyout, parent, false);

        return new MyFavoritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyFavoritesAdapter.ViewHolder holder, int position) {

            holder.textViewSourceDescription.setText(myNews.get(position).getSourceDescription());
            holder.textViewSourceName.setText(myNews.get(position).getSourceName());
            holder.textViewTime.setText(myNews.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return myNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSourceName,textViewSourceDescription,textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewSourceName = (TextView)itemView.findViewById(R.id.textViewSourceName2);
            textViewSourceDescription = (TextView)itemView.findViewById(R.id.textViewSourceDescription2);
            textViewTime = (TextView)itemView.findViewById(R.id.textViewTime2);

        }
    }
}
