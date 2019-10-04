package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolai.mynews.Controllers.Models.SearchArticlesArticle;
import com.nikolai.mynews.Controllers.Models.URL;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * Class that actually holds the articles in each row of the recyclerview
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
//        RequestOptions options = new Picas()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round);

        if (articleSearchArticle.getMultimedia().size() == 0) {
            articleSearchArticle.getMultimedia().add(new URL("http://www.google.com"));
        }

        this.textView.setText(articleSearchArticle.getSnippet());
            Picasso.get().load("https://static01.nyt.com/" + articleSearchArticle.getMultimedia().get(0).getUrl())
//                .apply(options)
                .into(imageView);

        this.callbackWeakRef = new WeakReference<ArticleSearchArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        ArticleSearchArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
