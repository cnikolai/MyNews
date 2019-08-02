package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
import com.nikolai.mynews.R;

import java.util.List;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class TopStoriesArticleAdapter extends RecyclerView.Adapter<TopStoriesArticleViewHolder> {

    private static final String TAG = "TopStoriesArtAdapter";

    public interface Listener {
        void onClickDeleteButton(int position);
        void changeArticleColor(int position, TextView textview, Context context);
    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    private final List<TopStoriesArticle> mTopStoriesArticles;
    private final RequestManager glide;

    // CONSTRUCTOR
    public TopStoriesArticleAdapter(List<TopStoriesArticle> topStoriesArticles, RequestManager glide, Listener callback) {
        this.mTopStoriesArticles = topStoriesArticles;
        this.glide = glide;
        this.callback = callback;
    }

    @Override
    public TopStoriesArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news_main_item, parent, false);

        return new TopStoriesArticleViewHolder(view, context);
    }

    // UPDATE VIEW HOLDER WITH A NEWSARTICLE
    @Override
    public void onBindViewHolder(TopStoriesArticleViewHolder viewHolder, int position) {
        viewHolder.updateWithNewsArticle(this.mTopStoriesArticles.get(position), this.callback);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mTopStoriesArticles.size();
    }

    public TopStoriesArticle getArticle(int position){
        return this.mTopStoriesArticles.get(position);
    }

    public void deleteArticle(int position){
        Log.d(TAG, "deleteArticle: inside");
        this.mTopStoriesArticles.remove(position);
    }

//    public void changeArticleColor(int position, View textview, Context context){
//        Log.d(TAG, "changeArticleColor: inside");
//        textview.setTextColor(ContextCompat.getColor(context, R.color.orange));
//    }

}
