package com.nikolai.mynews.Controllers.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.nikolai.mynews.Controllers.Activities.WebViewActivity;
import com.nikolai.mynews.Controllers.Models.SearchArticles;
import com.nikolai.mynews.Controllers.Models.SearchArticlesArticle;
import com.nikolai.mynews.Controllers.Utils.ArticleSearchArticleStreams;
import com.nikolai.mynews.Controllers.Utils.ItemClickSupport;
import com.nikolai.mynews.Controllers.Views.ArticleSearchArticleAdapter;
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

public class SearchResultsFragment extends Fragment implements ArticleSearchArticleAdapter.Listener {

    private Context mContext;
    public static SearchResultsFragment newInstance() {
        return (new SearchResultsFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        View view;
        Toast.makeText(getContext(), "Inside Fragment", Toast.LENGTH_LONG).show();
        //if (arguments != null && arguments.containsKey("edttext")) {
        if (arguments != null) {
            Toast.makeText(getContext(), "Inside Fragment", Toast.LENGTH_SHORT).show();
            Boolean chkArts = getArguments().getBoolean("chkArts");
            Boolean chkBusiness = getArguments().getBoolean("chkBusiness");
            Boolean chkEntrepreneurs = getArguments().getBoolean("chkEntrepreneurs");
            Boolean chkPolitics = getArguments().getBoolean("chkPolitics");
            Boolean chkSports = getArguments().getBoolean("chkSports");
            Boolean chkTravel = getArguments().getBoolean("chkTravel");
            String mBeginDateEditText = getArguments().getString("mBeginDateEditText");
            String mEndDateEditText = getArguments().getString("mEndDateEditText");
            String searchqueryterm = getArguments().getString("searchqueryterm");

            view = inflater.inflate(R.layout.fragment_search_news_main, container, false);
            ButterKnife.bind(this, view);
            this.configureRecyclerView();
            this.configureSwipeRefreshLayout();
            this.configureOnClickRecyclerView();
            this.executeHttpRequestWithRetrofit();
            this.mContext = this.getContext();

            //TODO: come back to this
//        mProgressDialog = new ProgressDialog(SearchArticlesFragment.newInstance().getContext());
//        mProgressDialog.setMessage("Loading....");
//        mProgressDialog.show();
        }
        else {
            view = inflater.inflate(R.layout.fragment_before_any_search_results, container, false);
        }
            return view;
    }

    // FOR DESIGN
    @BindView(R.id.fragment_search_news_main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_search_news_main_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<SearchArticlesArticle> mSearchArticlesArticles;
    private ArticleSearchArticleAdapter adapter;
    ProgressDialog mProgressDialog;

    public SearchResultsFragment() { }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_search_news_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        SearchArticlesArticle searchArticlesArticle = adapter.getArticle(position);
                        //Toast.makeText(getContext(), "You clicked on article : ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("URL", searchArticlesArticle.getWeb_url());
                        startActivity(intent);
                    }
                });
    }

    public void onClickDeleteButton(int position) {
        SearchArticlesArticle searchArticlesArticle = adapter.getArticle(position);
        Toast.makeText(getContext(), "You are trying to delete article : ", Toast.LENGTH_SHORT).show();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    //TODO: come back to this
    private void configureRecyclerView(){
        this.mSearchArticlesArticles = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new ArticleSearchArticleAdapter(this.mSearchArticlesArticles, Glide.with(this), this);
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
        this.disposable = ArticleSearchArticleStreams.streamFetchNewsArticle().subscribeWith(new DisposableObserver<SearchArticles>() {
            @Override
            public void onNext(SearchArticles searchArticles) {
                updateUI(searchArticles);
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
    private void updateUI(SearchArticles searchArticles){
        this.mSearchArticlesArticles.clear();
        this.mSearchArticlesArticles.addAll(searchArticles.getResults());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
