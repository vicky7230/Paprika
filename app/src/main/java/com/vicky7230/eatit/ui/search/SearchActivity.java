package com.vicky7230.eatit.ui.search;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SearchActivity extends BaseActivity implements SearchMvpView {

    @Inject
    SearchMvpPresenter<SearchMvpView> presenter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    SearchAdapter searchAdapter;

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

    private void init() {
        presenter.instantSearch(searchEditText);
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
}
