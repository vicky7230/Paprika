package com.vicky7230.eatit.ui.search;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class SearchActivity extends BaseActivity implements SearchMvpView {

    @Inject
    SearchMvpPresenter<SearchMvpView> presenter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    SearchAdapter searchAdapter;

    @BindView(R.id.back_image_view)
    AppCompatImageView backImageView;
    @BindView(R.id.clear_image_view)
    AppCompatImageView clearImageView;
    @BindView(R.id.search_edit_text)
    AppCompatEditText searchEditText;
    @BindView(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        init();
    }

    @OnClick(R.id.back_image_view)
    public void goBack(View view) {
        finish();
    }

    @OnClick(R.id.clear_image_view)
    public void clearSearchText(View view) {
        searchEditText.setText("");
    }

    private void init() {
//        presenter.instantSearch(searchEditText);
        presenter.instantSearch(RxTextView.afterTextChangeEvents(searchEditText));
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void reFreshRecipeList(List<Recipe> recipeList) {
        searchAdapter.refreshItems(recipeList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
