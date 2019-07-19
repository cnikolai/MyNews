package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.request.RequestOptions;
import com.nikolai.mynews.Controllers.Models.NewsArticle;
import com.nikolai.mynews.Controllers.Models.URL;
import com.nikolai.mynews.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class NewsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String TAG = "NewsArticleViewHolder";

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    @BindView(R.id.fragment_main_item_title) TextView textView;
    //@BindView(R.id.fragment_main_item_website) TextView texViewWebsite;
    @BindView(R.id.fragment_main_item_date) TextView textViewDate;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;
    @BindView(R.id.fragment_main_item_delete) ImageButton imageButton;

    private WeakReference<NewsArticleAdapter.Listener> callbackWeakRef;
    private Context context;

    public NewsArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
    }

    //TODO: COME BACK TO THIS
    public void updateWithNewsArticle(NewsArticle NewsArticle, RequestManager glide, NewsArticleAdapter.Listener callback) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        if (NewsArticle.getMultimedia().size() == 0) {
            NewsArticle.getMultimedia().add(new URL("http://1111aaaa"));
        }

        this.textView.setText(NewsArticle.getTitle());
        String strDate = dateFormat.format(NewsArticle.getPublished_date());
        //Log.d(TAG, strDate);
        this.textViewDate.setText(strDate);
        //this.texViewWebsite.setText(githubUser.getHtmlUrl());
        Glide.with(context).load(NewsArticle.getMultimedia().get(0).getUrl()).apply(options).into(imageView);
        this.imageButton.setOnClickListener(this);

        this.callbackWeakRef = new WeakReference<NewsArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        NewsArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
