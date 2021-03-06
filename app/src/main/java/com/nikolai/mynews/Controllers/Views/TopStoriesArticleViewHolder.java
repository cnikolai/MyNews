package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolai.mynews.ArticleBeenRead;
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
import com.nikolai.mynews.Controllers.Models.URL;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * Class that actually holds the articles in each row of the recyclerview
 */
public class TopStoriesArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = TopStoriesArticleViewHolder.class.getSimpleName();

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @BindView(R.id.fragment_main_item_title) TextView textView;
    @BindView(R.id.fragment_main_item_date) TextView textViewDate;
    @BindView(R.id.fragment_main_item_section) TextView textViewSection;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;

    private WeakReference<TopStoriesArticleAdapter.Listener> callbackWeakRef;
    private final Context context;

    public TopStoriesArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void updateWithNewsArticle(TopStoriesArticle TopStoriesArticle, TopStoriesArticleAdapter.Listener callback) {
        String ending = "";

        if (TopStoriesArticle.getMultimedia().size() == 0) {
            TopStoriesArticle.getMultimedia().add(new URL("http://www.google.com"));
        }
        if (!TopStoriesArticle.getSubsection().isEmpty()) {
            ending = " > "+ TopStoriesArticle.getSubsection();
        }

        //check to see if the id is in the read id's or not and change the color
        if (ArticleBeenRead.getInstance().hasBeenRead(TopStoriesArticle.getUrl())) {
            this.textView.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }
        else {
            this.textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
        this.textView.setText(TopStoriesArticle.getTitle());
        String strDate = dateFormat.format(TopStoriesArticle.getPublished_date());
        this.textViewDate.setText(strDate);
        //using temp string for internationalization
        String temp = TopStoriesArticle.getSection() + ending;
        this.textViewSection.setText(temp);
        this.textViewSection.setTypeface(null, Typeface.BOLD);
        Picasso.get().load(TopStoriesArticle.getMultimedia().get(0).getUrl())
                .error(R.mipmap.ic_launcher_round)
            .into(imageView);

        this.callbackWeakRef = new WeakReference<TopStoriesArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        TopStoriesArticleAdapter.Listener callback = callbackWeakRef.get();
    }
}
