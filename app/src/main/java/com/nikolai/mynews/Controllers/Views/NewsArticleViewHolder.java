package com.nikolai.mynews.Controllers.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.nikolai.mynews.Controllers.Models.NewsArticle;
import com.nikolai.mynews.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class NewsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    @BindView(R.id.fragment_main_item_title) TextView textView;
    @BindView(R.id.fragment_main_item_website) TextView texViewWebsite;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;
    @BindView(R.id.fragment_main_item_delete) ImageButton imageButton;

    private WeakReference<NewsArticleAdapter.Listener> callbackWeakRef;

    public NewsArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //TODO: COME BACK TO THIS
    public void updateWithNewsArticle(NewsArticle NewsArticle, RequestManager glide, NewsArticleAdapter.Listener callback){
        this.textView.setText(NewsArticle.getTitle());
        //this.texViewWebsite.setText(githubUser.getHtmlUrl());
        //glide.load(githubUser.getAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);
        //this.imageButton.setOnClickListener(this);
        this.callbackWeakRef = new WeakReference<NewsArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        NewsArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
