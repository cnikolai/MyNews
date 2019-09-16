package com.nikolai.mynews.Controllers.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikolai.mynews.Controllers.Models.MostPopularArticle;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
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

    public void updateWithNewsArticle(MostPopularArticle MostPopularArticle, MostPopularArticleAdapter.Listener callback) {
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round);
        String ending = "";


        this.textView.setText(MostPopularArticle.getTitle());
        String strDate = dateFormat.format(MostPopularArticle.getPublished_date());
        this.textViewDate.setText(strDate);
        String temp = MostPopularArticle.getSection() + ending;
        this.textViewSection.setText(temp);
        this.textViewSection.setTypeface(null, Typeface.BOLD);
        Picasso.get().load(MostPopularArticle.getMedia().get(0).getMedia_metadata().get(0).getUrl())
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
