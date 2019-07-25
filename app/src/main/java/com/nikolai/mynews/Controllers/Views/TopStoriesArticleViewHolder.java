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
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
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

public class TopStoriesArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String TAG = "TopStoriesArticleViewHolder";

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    @BindView(R.id.fragment_main_item_title) TextView textView;
    //@BindView(R.id.fragment_main_item_website) TextView texViewWebsite;
    @BindView(R.id.fragment_main_item_date) TextView textViewDate;
    @BindView(R.id.fragment_main_item_section) TextView textViewSection;
    ;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;
    @BindView(R.id.fragment_main_item_delete) ImageButton imageButton;

    private WeakReference<TopStoriesArticleAdapter.Listener> callbackWeakRef;
    private Context context;

    public TopStoriesArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
    }

    //TODO: COME BACK TO THIS
    public void updateWithNewsArticle(TopStoriesArticle TopStoriesArticle, RequestManager glide, TopStoriesArticleAdapter.Listener callback) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        String ending = "";

        if (TopStoriesArticle.getMultimedia().size() == 0) {
            TopStoriesArticle.getMultimedia().add(new URL("http://1111aaaa"));
        }
        if (TopStoriesArticle.getSubsection() != "") {
            ending = ">"+ TopStoriesArticle.getSubsection();
        }

        this.textView.setText(TopStoriesArticle.getTitle());
        String strDate = dateFormat.format(TopStoriesArticle.getPublished_date());
        this.textViewDate.setText(strDate);
        this.textViewSection.setText(TopStoriesArticle.getSection() + ending);
        Glide.with(context).load(TopStoriesArticle.getMultimedia().get(0).getUrl()).apply(options).into(imageView);
        this.imageButton.setOnClickListener(this);

        this.callbackWeakRef = new WeakReference<TopStoriesArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        TopStoriesArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
