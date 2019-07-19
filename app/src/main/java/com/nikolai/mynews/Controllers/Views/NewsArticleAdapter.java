package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.nikolai.mynews.Controllers.Models.NewsArticle;
import com.nikolai.mynews.R;

import java.util.List;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleViewHolder> {

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    private List<NewsArticle> NewsArticles;
    private RequestManager glide;

    // CONSTRUCTOR
    public NewsArticleAdapter(List<NewsArticle> NewsArticles, RequestManager glide, Listener callback) {
        this.NewsArticles = NewsArticles;
        this.glide = glide;
        this.callback = callback;
    }

    @Override
    public NewsArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news_main_item, parent, false);

        return new NewsArticleViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A NEWSARTICLE
    @Override
    public void onBindViewHolder(NewsArticleViewHolder viewHolder, int position) {
        viewHolder.updateWithNewsArticle(this.NewsArticles.get(position), this.glide, this.callback);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.NewsArticles.size();
    }

    public NewsArticle getArticle(int position){
        return this.NewsArticles.get(position);
    }
}
