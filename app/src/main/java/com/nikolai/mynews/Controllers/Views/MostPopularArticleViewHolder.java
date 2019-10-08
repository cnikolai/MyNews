package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolai.mynews.ArticleBeenRead;
import com.nikolai.mynews.Controllers.Models.MostPopularArticle;
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
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
public class MostPopularArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @BindView(R.id.fragment_most_popular_item_title) TextView textView;
    //@BindView(R.id.fragment_most_popular_item_website) TextView texViewWebsite;
    @BindView(R.id.fragment_most_popular_item_date) TextView textViewDate;
    @BindView(R.id.fragment_most_popular_item_section) TextView textViewSection;
    ;
    @BindView(R.id.fragment_most_popular_item_image) ImageView imageView;
    //@BindView(R.id.fragment_most_popular_item_delete) ImageButton imageButton;

    private WeakReference<MostPopularArticleAdapter.Listener> callbackWeakRef;
    private final Context context;

    public MostPopularArticleViewHolder(View itemView, Context context) {
        super(itemView);
        this.context=context;
        ButterKnife.bind(this, itemView);
    }

    public void updateWithNewsArticle(MostPopularArticle mostPopularArticle, MostPopularArticleAdapter.Listener callback) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round);
        String ending = "";

        //check to see if the id is in the read id's or not and change the color
        if (ArticleBeenRead.getInstance().hasBeenRead(mostPopularArticle.getUrl())) {
            this.textView.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }
        else {
            this.textView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
        this.textView.setText(mostPopularArticle.getTitle());
        String strDate = dateFormat.format(mostPopularArticle.getPublished_date());
        this.textViewDate.setText(strDate);
        String temp = mostPopularArticle.getSection() + ending;
        this.textViewSection.setText(temp);
        this.textViewSection.setTypeface(null, Typeface.BOLD);
        Picasso.get().load(mostPopularArticle.getMedia().get(0).getMedia_metadata().get(0).getUrl())
                .error(R.mipmap.ic_launcher_round)
//            .apply(options)
            .into(imageView);

        this.callbackWeakRef = new WeakReference<MostPopularArticleAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        MostPopularArticleAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
