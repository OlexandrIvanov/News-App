package com.example.user.newsapp.View;


import android.annotation.SuppressLint;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.example.user.newsapp.Adapter.MyAdapter;
import com.example.user.newsapp.Database.MyNews;
import com.example.user.newsapp.Model.ModelSources.AllNewsSources;
import com.example.user.newsapp.Model.ModelSources.Source;
import com.example.user.newsapp.Model.ModelTopNews.Article;
import com.example.user.newsapp.Model.ModelTopNews.PostModel;
import com.example.user.newsapp.Net.App;
import com.example.user.newsapp.Net.NewsApiReqRequest;
import com.example.user.newsapp.R;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private ShareDialog shareDialog;
    private Spinner spinner;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(this);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        spinner =  findViewById(R.id.spinner);
        setSpinner();

        shareDialog = new ShareDialog(this);

        Button btnGoToFavorAct = findViewById(R.id.btnGoToFavorAct);
        btnGoToFavorAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setSpinner(){

        Resources res = getResources();

        String [] data = res.getStringArray(R.array.spinner_text);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        loadTopNews();

                        Log.d("Log"," Add ");
                        break;
                    case 1:
                        loadSources();

                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void loadTopNews(){
        NewsApiReqRequest newsApiReqRequest = App.getRetrofitInstance().create(NewsApiReqRequest.class);

        Call<PostModel> call = newsApiReqRequest.getTopNews();

        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {

                add_items_to_the_list(response.body().getArticles(),1);
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                getDialog();
            }
        });

    }

    private void loadSources(){
        NewsApiReqRequest newsApiReqRequest = App.getRetrofitInstance().create(NewsApiReqRequest.class);

        Call<AllNewsSources> call = newsApiReqRequest.getSources();

        call.enqueue(new Callback<AllNewsSources>() {
            @Override
            public void onResponse(Call<AllNewsSources> call, Response<AllNewsSources> response) {
                add_items_to_the_list(response.body().getSources());
            }

            @Override
            public void onFailure(Call<AllNewsSources> call, Throwable t) {
                getDialog();
            }
        });
    }

    private void add_items_to_the_list(List<Article> articles, int whatNewsToDownload){

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MyAdapter(articles, this, whatNewsToDownload);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void add_items_to_the_list(List<Source> articles){

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MyAdapter(articles, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    private void getDialog(){
        AlertDialog.Builder   builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title)
                .setIcon(R.drawable.imagedanger)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_positive_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (spinner.getSelectedItemPosition()==0){
                            loadTopNews();
                        }else loadSources();

                    }
                })
                .setNegativeButton(R.string.dialog_negative_button_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public void shareWithFacebook(String newsUrl){

        ShareLinkContent linkContent = new  ShareLinkContent.Builder()
                .setQuote("")
                .setContentUrl(Uri.parse(newsUrl))
                .build();

        if (ShareDialog.canShow(ShareLinkContent.class)){
            shareDialog.show(linkContent);
        }
    }



    public void add_in_DB(String description,String name ,String time){

        realm.beginTransaction();
        MyNews book = realm.createObject(MyNews.class);
        book.setSourceDescription(description);
        book.setSourceName(name);
        book.setTime(time);

        realm.commitTransaction();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
