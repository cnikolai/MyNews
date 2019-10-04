package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikolai.mynews.Controllers.Models.SearchArticlesArticle;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * An adapter that provides miscellaneous functionality in support of the recyclerview
 */
public class ArticleSearchArticleAdapter extends RecyclerView.Adapter<ArticleSearchArticleViewHolder> {

    private static final String TAG = "ArtSearchArtAdapter";

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    private final List<SearchArticlesArticle> mSearchArticlesArticles;
    private final Picasso picasso;

    // CONSTRUCTOR
    public ArticleSearchArticleAdapter(List<SearchArticlesArticle> SearchArticlesArticles, Picasso picasso, Listener callback) {
        this.mSearchArticlesArticles = SearchArticlesArticles;
        this.picasso = picasso;
        this.callback = callback;
    }

    @Override
    public ArticleSearchArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_news_main_item, parent, false);

        return new ArticleSearchArticleViewHolder(view, context);
    }

    // UPDATE VIEW HOLDER WITH A NEWSARTICLE
    @Override
    public void onBindViewHolder(ArticleSearchArticleViewHolder viewHolder, int position) {
        viewHolder.updateWithNewsArticle(this.mSearchArticlesArticles.get(position), this.callback);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mSearchArticlesArticles.size();
    }

    public SearchArticlesArticle getArticle(int position){
        return this.mSearchArticlesArticles.get(position);
    }

    public void deleteArticle(int position){
        Log.d(TAG, "deleteArticle: inside");
        this.mSearchArticlesArticles.remove(position);
    }
}
