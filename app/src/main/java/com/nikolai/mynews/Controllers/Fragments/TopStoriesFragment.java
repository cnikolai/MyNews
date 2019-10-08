package com.nikolai.mynews.Controllers.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikolai.mynews.ArticleBeenRead;
import com.nikolai.mynews.Controllers.Activities.WebViewActivity;
import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Models.TopStoriesArticle;
import com.nikolai.mynews.Controllers.Utils.ItemClickSupport;
import com.nikolai.mynews.Controllers.Utils.TopStoriesArticleStreams;
import com.nikolai.mynews.Controllers.Views.TopStoriesArticleAdapter;
import com.nikolai.mynews.R;
import com.nikolai.mynews.SharedPreferencesWrapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

/**
 * Creates a recycler view for the top stories news articles.  Calls an HTTP request with retrofit to generate the articles for the recycler view
 */
public class TopStoriesFragment extends Fragment implements TopStoriesArticleAdapter.Listener {

    private TextView mTextView;
    private SharedPreferencesWrapper sharedPreferencesWrapper;

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesWrapper = new SharedPreferencesWrapper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_news_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        this.mContext = this.getContext();

        return view;
    }

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<TopStoriesArticle> mTopStoriesArticles;
    private TopStoriesArticleAdapter adapter;
    ProgressDialog mProgressDialog;

    public TopStoriesFragment() { }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_page_news_main_item)
        .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                TopStoriesArticle topStoriesArticle = adapter.getArticle(position);
                ArticleBeenRead.getInstance().setArticleHasBeenRead(topStoriesArticle.getUrl());
                adapter.notifyItemChanged(position);
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("URL", topStoriesArticle.getUrl());
                startActivity(intent);
            }
        });
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        this.mTopStoriesArticles = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new TopStoriesArticleAdapter(this.mTopStoriesArticles, Picasso.get(), this);
        // Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = TopStoriesArticleStreams.streamFetchNewsArticle().subscribeWith(new DisposableObserver<TopStories>() {
          @Override
          public void onNext(TopStories topStories) {
              //using shared prefs
              sharedPreferencesWrapper.saveLastUpdate(topStories.getLast_updated());
                updateUI(topStories);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(TopStories topStories){
        if (topStories.getResults().isEmpty()) {

        } else {

        }
        this.mTopStoriesArticles.clear();
        this.mTopStoriesArticles.addAll(topStories.getResults());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

}
