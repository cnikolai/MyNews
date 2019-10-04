package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikolai.mynews.Controllers.Models.MostPopularArticle;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * An adapter that provides miscellaneous functionality in support of the recyclerview
 */
public class MostPopularArticleAdapter extends RecyclerView.Adapter<MostPopularArticleViewHolder> {

    private static final String TAG = "MostPopularArtAdapter";
    private TextView mTextView;

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FOR COMMUNICATION
    private final Listener callback;

    // FOR DATA
    private final List<MostPopularArticle> mMostPopularArticles;
    private final Picasso picasso;

    // CONSTRUCTOR
    public MostPopularArticleAdapter(List<MostPopularArticle> mostPopularArticles, Picasso picasso, Listener callback) {
        this.mMostPopularArticles = mostPopularArticles;
        this.picasso = picasso;
        this.callback = callback;
    }

    @Override
    public MostPopularArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_most_popular_news_main_item
                , parent, false);

        return new MostPopularArticleViewHolder(view, context);
    }

    // UPDATE VIEW HOLDER WITH A NEWSARTICLE
    @Override
    public void onBindViewHolder(MostPopularArticleViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: inside");
        viewHolder.updateWithNewsArticle(this.mMostPopularArticles.get(position), this.callback);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mMostPopularArticles.size();
    }

    public MostPopularArticle getArticle(int position){
        Log.d(TAG, "getArticle: inside");
        return this.mMostPopularArticles.get(position);
    }

    public void changeArticleColor(int position, Context context) {
        mTextView = (TextView) mTextView.findViewById(R.id.fragment_most_popular_item_title);
        mTextView.setTextColor((ContextCompat.getColor(context, R.color.orange)));
    }

    public void deleteArticle(int position){
        Log.d(TAG, "deleteArticle: inside");
        this.mMostPopularArticles.remove(position);
    }
}
