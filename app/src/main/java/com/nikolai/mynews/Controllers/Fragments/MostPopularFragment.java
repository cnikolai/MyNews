package com.nikolai.mynews.Controllers.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nikolai.mynews.Controllers.Activities.WebViewActivity;
import com.nikolai.mynews.Controllers.Models.MostPopular;
import com.nikolai.mynews.Controllers.Models.MostPopularArticle;
import com.nikolai.mynews.Controllers.Utils.ItemClickSupport;
import com.nikolai.mynews.Controllers.Utils.MostPopularArticleStreams;
import com.nikolai.mynews.Controllers.Views.MostPopularArticleAdapter;
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
 * Creates a recycler view for the most popular news articles.  Calls an HTTP request with retrofit to generate the articles for the recycler view
 */
public class MostPopularFragment extends Fragment implements MostPopularArticleAdapter.Listener {

    private static final String TAG = "MostPopularFragment";

    public static MostPopularFragment newInstance() {
        Log.d(TAG, "newInstance: inside");
        return (new MostPopularFragment());
    }
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inside MostPopularFragment");
        //Toast.makeText(getContext(), "Inside MostPopularFragment", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.fragment_most_popular_news_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        this.mContext = this.getContext();

//        mProgressDialog = new ProgressDialog(this.getContext());
//        mProgressDialog.setMessage("Loading....");
//        mProgressDialog.show();

        return view;
    }

    // FOR DESIGN
    @BindView(R.id.fragment_most_popular_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_most_popular_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<MostPopularArticle> mMostPopularArticles;
    private MostPopularArticleAdapter adapter;
    ProgressDialog mProgressDialog;

    public MostPopularFragment() { }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: inside");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    private void configureOnClickRecyclerView(){
        Log.d(TAG, "configureOnClickRecyclerView: inside");
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_most_popular_news_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        MostPopularArticle mostPopularArticle = adapter.getArticle(position);
                        //Toast.makeText(getContext(), "You clicked on article : ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("URL", mostPopularArticle.getUrl());
                        startActivity(intent);
                    }
                });
    }

    public void onClickDeleteButton(int position) {
        MostPopularArticle mostPopularArticle = adapter.getArticle(position);
        Toast.makeText(getContext(), "You are trying to delete article at position: "+position, Toast.LENGTH_SHORT).show();
        adapter.deleteArticle(position);
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        Log.d(TAG, "configureRecyclerView: inside");
        this.mMostPopularArticles = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new MostPopularArticleAdapter(this.mMostPopularArticles, Picasso.get(), this);
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
        Log.d(TAG, "executeHttpRequestWithRetrofit: inside");
        this.disposable = MostPopularArticleStreams.streamFetchNewsArticle().subscribeWith(new DisposableObserver<MostPopular>() {
            @Override
            public void onNext(MostPopular mostPopular) {
                Log.d(TAG, "onNext: inside");
                updateUI(mostPopular);
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

    private void updateUI(MostPopular mostPopular){
        Log.d(TAG, "updateUI: inside");
        this.mMostPopularArticles.clear();
        this.mMostPopularArticles.addAll(mostPopular.getResults());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        //mProgressDialog.cancel();
        //if (mProgressDialog.isShowing())
        //    mProgressDialog.dismiss();
    }
}
