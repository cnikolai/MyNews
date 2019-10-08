package com.nikolai.mynews.Controllers.Fragments;

import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nikolai.mynews.ArticleBeenRead;
import com.nikolai.mynews.Controllers.Activities.WebViewActivity;
import com.nikolai.mynews.Controllers.Models.SearchArticles;
import com.nikolai.mynews.Controllers.Models.SearchArticlesArticle;
import com.nikolai.mynews.Controllers.Utils.ArticleSearchArticleStreams;
import com.nikolai.mynews.Controllers.Utils.ItemClickSupport;
import com.nikolai.mynews.Controllers.Views.ArticleSearchArticleAdapter;
import com.nikolai.mynews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
 * Creates a recycler view for the search results news articles.  Calls an HTTP request with retrofit to generate the articles for the recycler view
 */
public class SearchResultsFragment extends Fragment implements ArticleSearchArticleAdapter.Listener {

    private static final String TAG = "SearchResultsFragment";
    private Context mContext;

    public static SearchResultsFragment newInstance() {
        return (new SearchResultsFragment());
    }

    private Boolean chkArts;
    private Boolean chkBusiness;
    private Boolean chkEntrepreneurs;
    private Boolean chkPolitics;
    private Boolean chkSports;
    private Boolean chkTravel;
    private Boolean chkScience;
    private Boolean chkTechnology;
    private Boolean chkWorld;

    private String mBeginDateEditText;
    private String mEndDateEditText;
    private String searchqueryterm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        Log.d(TAG, "onCreateView: inside SearchResultsFragment");

            view = inflater.inflate(R.layout.fragment_search_news_main, container, false);
            ButterKnife.bind(this, view);
            this.configureRecyclerView();
            this.configureSwipeRefreshLayout();
            this.configureOnClickRecyclerView();
            this.mContext = this.getContext();

            return view;
    }

    // FOR DESIGN
    @BindView(R.id.fragment_search_news_main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_search_results)
    View noSearchResultsView;
    @BindView(R.id.initial_no_search_results)
    View initialNoSearchResultsView;
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
                    public void onItemClicked(int position) {
                        SearchArticlesArticle searchArticlesArticle = adapter.getArticle(position);
                        ArticleBeenRead.getInstance().setArticleHasBeenRead(searchArticlesArticle.getWeb_url());
                        adapter.notifyItemChanged(position);
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("URL", searchArticlesArticle.getWeb_url());
                        startActivity(intent);
                    }
                });
    }

    public void onClickDeleteButton(int position) {
        SearchArticlesArticle searchArticlesArticle = adapter.getArticle(position);
        Toast.makeText(getContext(), "You are trying to delete article at position: "+ position, Toast.LENGTH_SHORT).show();
        adapter.deleteArticle(position);
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        this.mSearchArticlesArticles = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new ArticleSearchArticleAdapter(this.mSearchArticlesArticles, Picasso.get(), this);
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
        Log.d(TAG, "inside executeHttpRequestWithRetrofit: ");
        this.disposable = ArticleSearchArticleStreams.streamFetchNewsArticle(chkArts,chkBusiness,chkEntrepreneurs,chkPolitics,chkSports,chkTravel,chkScience, chkTechnology, chkWorld, mBeginDateEditText,mEndDateEditText,searchqueryterm).subscribeWith(new DisposableObserver<SearchArticles>() {
            @Override
            public void onNext(SearchArticles searchArticles) {
                Log.d(TAG, "inside onNext: ");
                updateUI(searchArticles);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "inside onError: ");
                Log.e(TAG, "exception", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "inside onComplete: ");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(SearchArticles searchArticles){
        initialNoSearchResultsView.setVisibility(View.GONE);
        Log.d(TAG, "inside updateUI: ");
        int temp = searchArticles.getResponse().getDocs().size();
        Log.d(TAG, "updateUI: "+temp);
        if (searchArticles.getResponse().getDocs().size() == 0) {
            noSearchResultsView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        } else {
            noSearchResultsView.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
        this.mSearchArticlesArticles.clear();
        this.mSearchArticlesArticles.addAll(searchArticles.getResponse().getDocs());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setSearch(Bundle extras) {
        if (extras != null) {
            Log.d(TAG, "inside setsearch extras not null");
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                Log.d(TAG, String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName()));
            }
        }
        chkArts = extras.getBoolean("chkArts");
        chkBusiness = extras.getBoolean("chkBusiness");
        chkEntrepreneurs = extras.getBoolean("chkEntrepreneurs");
        chkPolitics = extras.getBoolean("chkPolitics");
        chkSports = extras.getBoolean("chkSports");
        chkTravel = extras.getBoolean("chkTravel");
        chkScience = extras.getBoolean("chkScience");
        chkTechnology = extras.getBoolean("chkTechnology");
        chkWorld = extras.getBoolean("chkWorld");
        mBeginDateEditText = extras.getString("mBeginDateEditText");
        mEndDateEditText = extras.getString("mEndDateEditText");
        searchqueryterm = extras.getString("searchqueryterm");
        this.executeHttpRequestWithRetrofit();
    }
}
