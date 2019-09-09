package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nikolai.mynews.Controllers.Models.SearchArticlesArticle;
import com.nikolai.mynews.Controllers.Models.URL;
import com.nikolai.mynews.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class ArticleSearchArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String TAG = "ArticleSearchArticleViewHolder";

    @BindView(R.id.fragment_main_item_title) TextView textView;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;

    private WeakReference<ArticleSearchArticleAdapter.Listener> callbackWeakRef;
    private final Context context;

    public ArticleSearchArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
    }

    public void updateWithNewsArticle(SearchArticlesArticle articleSearchArticle, ArticleSearchArticleAdapter.Listener callback) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        if (articleSearchArticle.getMultimedia().size() == 0) {
            articleSearchArticle.getMultimedia().add(new URL("http://www.google.com"));
        }

        this.textView.setText(articleSearchArticle.getSnippet());
            Glide.with(context).load("https://static01.nyt.com/" + articleSearchArticle.getMultimedia().get(0).getUrl()).apply(options).into(imageView);

        this.callbackWeakRef = new WeakReference<ArticleSearchArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        ArticleSearchArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
