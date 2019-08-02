package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
import com.nikolai.mynews.Controllers.Models.URL;
import com.nikolai.mynews.R;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class TopStoriesArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = "TopStoriesArticleViewHolder";

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @BindView(R.id.fragment_main_item_title) TextView textView;
    //@BindView(R.id.fragment_main_item_website) TextView texViewWebsite;
    @BindView(R.id.fragment_main_item_date) TextView textViewDate;
    @BindView(R.id.fragment_main_item_section) TextView textViewSection;
    ;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;
    //@BindView(R.id.fragment_main_item_delete) ImageButton imageButton;

    private WeakReference<TopStoriesArticleAdapter.Listener> callbackWeakRef;
    private final Context context;

    public TopStoriesArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
    }

    public void updateWithNewsArticle(TopStoriesArticle TopStoriesArticle, TopStoriesArticleAdapter.Listener callback) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        String ending = "";

        if (TopStoriesArticle.getMultimedia().size() == 0) {
            TopStoriesArticle.getMultimedia().add(new URL("http://www.google.com"));
        }
        if (!TopStoriesArticle.getSubsection().isEmpty()) {
            ending = " > "+ TopStoriesArticle.getSubsection();
        }

        this.textView.setText(TopStoriesArticle.getTitle());
        String strDate = dateFormat.format(TopStoriesArticle.getPublished_date());
        this.textViewDate.setText(strDate);
        //using temp string for internationalization
        String temp = TopStoriesArticle.getSection() + ending;
        this.textViewSection.setText(temp);
        this.textViewSection.setTypeface(null, Typeface.BOLD);
        Glide.with(context).load(TopStoriesArticle.getMultimedia().get(0).getUrl()).apply(options).into(imageView);

        this.callbackWeakRef = new WeakReference<TopStoriesArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        TopStoriesArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
            callback.changeArticleColor(getAdapterPosition(), this.textView, context);
        }
        //textView.setTextColor(ContextCompat.getColor(context, R.color.orange));
        //view.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
    }

    public TextView getTextView() {
        return textView;
    }
}
