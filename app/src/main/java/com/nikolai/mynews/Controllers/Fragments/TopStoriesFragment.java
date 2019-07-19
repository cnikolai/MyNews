package com.nikolai.mynews.Controllers.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikolai.mynews.Controllers.Models.NewsArticle;
import com.nikolai.mynews.Controllers.Models.TopStories;
import com.nikolai.mynews.Controllers.Utils.ItemClickSupport;
import com.nikolai.mynews.Controllers.Utils.NewsArticleStreams;
import com.nikolai.mynews.Controllers.Views.NewsArticleAdapter;
import com.nikolai.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Cynthia Nikolai on 7/11/2019.
 */

public class TopStoriesFragment extends Fragment implements NewsArticleAdapter.Listener {

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_news_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();

//        mProgressDialog = new ProgressDialog(TopStoriesFragment.newInstance().getContext());
//        mProgressDialog.setMessage("Loading....");
//        mProgressDialog.show();

        return view;
    }

    // FOR DESIGN
    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<NewsArticle> NewsArticles;
    private NewsArticleAdapter adapter;
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
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                NewsArticle NewsArticles = adapter.getArticle(position);
                Toast.makeText(getContext(), "You clicked on user : ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickDeleteButton(int position) {
        NewsArticle NewsArticles = adapter.getArticle(position);
        Toast.makeText(getContext(), "You are trying to delete user : ", Toast.LENGTH_SHORT).show();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    //TODO: come back to this
    private void configureRecyclerView(){
        this.NewsArticles = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new NewsArticleAdapter(this.NewsArticles, Glide.with(this), this);
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

    //TODO: come back to this
    private void executeHttpRequestWithRetrofit(){
        this.disposable = NewsArticleStreams.streamFetchNewsArticle().subscribeWith(new DisposableObserver<TopStories>() {
          @Override
          public void onNext(TopStories topStories) {
                updateUI(topStories);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });
    }

    // TODO: come back to this
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    // TODO: come back to this
    private void updateUI(TopStories topStories){
        this.NewsArticles.clear();
        this.NewsArticles.addAll(topStories.getResults());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
