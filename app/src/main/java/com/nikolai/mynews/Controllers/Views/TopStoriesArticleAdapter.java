package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;
import com.nikolai.mynews.ArticleBeenRead;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * An adapter that provides miscellaneous functionality in support of the recyclerview
 */
public class TopStoriesArticleAdapter extends RecyclerView.Adapter<TopStoriesArticleViewHolder> {

    private static final String TAG = TopStoriesArticleAdapter.class.getSimpleName();
    private TextView mTextView;

    public interface Listener {
    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    private final List<TopStoriesArticle> mTopStoriesArticles;
    private final Picasso picasso;

    // CONSTRUCTOR
    public TopStoriesArticleAdapter(List<TopStoriesArticle> topStoriesArticles, Picasso picasso, Listener callback) {
        this.mTopStoriesArticles = topStoriesArticles;
        this.picasso = picasso;
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

}
