package com.example.user.newsapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.user.newsapp.Adapter.MyAdapter;
import com.example.user.newsapp.Adapter.MyFavoritesAdapter;
import com.example.user.newsapp.Database.MyNews;
import com.example.user.newsapp.R;

import io.realm.Realm;

public class FavoritesActivity extends AppCompatActivity {

    private MyFavoritesAdapter adapter;
    private RecyclerView recyclerView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        realm = Realm.getInstance(this);


        recyclerView = (RecyclerView) findViewById(R.id.favoritesRecyclerView);
        adapter = new MyFavoritesAdapter(realm.allObjects(MyNews.class));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FavoritesActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
