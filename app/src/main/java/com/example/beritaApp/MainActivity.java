package com.example.beritaApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.beritaApp.newsapi.NewsApiClient;
import com.example.beritaApp.newsapi.models.Article;
import com.example.beritaApp.newsapi.models.ArticleAdapter;
import com.example.beritaApp.newsapi.models.request.EverythingRequest;
import com.example.beritaApp.newsapi.models.request.TopHeadlinesRequest;
import com.example.beritaApp.newsapi.models.response.ArticleResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int homeFragment = 1;
    private static final int bookmarksFragment = 2;
    private int currentFragment;

    private EditText searchBar;
    private ImageButton searchBtn;

    private ImageButton homeBtn;
    private ImageButton bookmarkBtn;
    private ImageView status;

    private List<Article> articleList;
    private RecyclerView newsRV;
    private ArticleAdapter articleAdapter;

    private String searchKey;

    private NewsApiClient newsApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.newsRV);

        searchKey = "";

        newsApiClient = new NewsApiClient("bc53295eaede4d4da0743c5c1f29b14e");
        getTopHeadLines(newsApiClient);


    }



    private void getEverything(NewsApiClient newsApiClient) {
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("trump")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        articleList = response.getArticles();
                        System.out.println("aarr: " + articleList.get(0).getSource());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println("err: " + throwable.getMessage());
                    }
                }
        );
    }

    private void getTopHeadLines(NewsApiClient newsApiClient) {
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q(searchKey)
                        .country("id")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        articleList = response.getArticles();
                        articleAdapter = new ArticleAdapter(articleList, getApplicationContext());
                        newsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        newsRV.setItemAnimator(new DefaultItemAnimator());
                        newsRV.setAdapter(articleAdapter);
                        System.out.println("aarr: " + articleList.get(0).getSource().getName());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println("err: " + throwable.getMessage());
                    }
                }
        );
    }

}