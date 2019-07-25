package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.nikolai.mynews.Controllers.Models.SearchArticlesArticle;
import com.nikolai.mynews.Controllers.Models.URL;
import com.nikolai.mynews.R;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class ArticleSearchArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String TAG = "ArticleSearchArticleViewHolder";

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

//    @BindView(R.id.fragment_main_item_title) TextView textView;
//    @BindView(R.id.fragment_main_item_date) TextView textViewDate;
//    @BindView(R.id.fragment_main_item_section) TextView textViewSection;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;
    @BindView(R.id.fragment_main_item_delete) ImageButton imageButton;

    private WeakReference<ArticleSearchArticleAdapter.Listener> callbackWeakRef;
    private Context context;

    public ArticleSearchArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
    }

    //TODO: COME BACK TO THIS
    public void updateWithNewsArticle(SearchArticlesArticle articleSearchArticle, RequestManager glide, ArticleSearchArticleAdapter.Listener callback) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        String ending = "";

        if (articleSearchArticle.getMultimedia().size() == 0) {
            articleSearchArticle.getMultimedia().add(new URL("http://1111aaaa"));
        }

//        String strDate = dateFormat.format(ArticleSearchArticle.getPublished_date());
//        this.textViewDate.setText(strDate);
//        this.textViewSection.setText(ArticleSearchArticle.getSection() + ending);
        Glide.with(context).load(articleSearchArticle.getMultimedia().get(0).getUrl()).apply(options).into(imageView);
        this.imageButton.setOnClickListener(this);

        this.callbackWeakRef = new WeakReference<ArticleSearchArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        ArticleSearchArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
