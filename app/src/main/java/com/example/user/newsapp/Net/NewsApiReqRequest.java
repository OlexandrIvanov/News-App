package com.example.user.newsapp.Net;



import com.example.user.newsapp.Model.ModelSources.AllNewsSources;
import com.example.user.newsapp.Model.ModelTopNews.PostModel;


import retrofit2.Call;
import retrofit2.http.GET;


public interface  NewsApiReqRequest {

    @GET("v2/top-headlines?country=us&apiKey=c6edbe527835404a9e39a957ae63d281")
    Call<PostModel> getTopNews();


    @GET("v2/sources?apiKey=c6edbe527835404a9e39a957ae63d281")
    Call<AllNewsSources> getSources();







}
