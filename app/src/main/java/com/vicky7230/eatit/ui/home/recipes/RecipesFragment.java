package com.vicky7230.eatit.ui.home.recipes;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.BaseFragment;
import com.vicky7230.eatit.ui.search.SearchActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class RecipesFragment extends BaseFragment implements RecipesMvpView, RecipesAdapter.Callback {

    @Inject
    RecipesMvpPresenter<RecipesMvpView> presenter;
    @Inject
    RecipesAdapter recipesAdapter;
    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @BindView(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private boolean isLoading = false;
    private boolean isScrollEnabled = false;

    public static RecipesFragment newInstance() {
        Bundle args = new Bundle();
        RecipesFragment fragment = new RecipesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        presenter.onAttach(this);
        ButterKnife.bind(this, view);
        recipesAdapter.setCallback(this);
        return view;
    }

    @Override
    protected void setUp(View view) {

        recipesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        recipesRecyclerView.setItemAnimator(new RecipesItemAnimator());
        recipesRecyclerView.addItemDecoration(new ItemOffsetDecoration(15));
        recipesRecyclerView.setAdapter(recipesAdapter);
        recipesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (floatingActionButton.isShown()) {
                        floatingActionButton.hide();
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!floatingActionButton.isShown()) {
                        floatingActionButton.show();
                    }
                }

                int visibleItemCount = staggeredGridLayoutManager.getChildCount();
                int totalItemCount = staggeredGridLayoutManager.getItemCount();
                int[] firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
                int pastVisibleItems = 0;

                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading && isScrollEnabled) {
                    recipesAdapter.addItem(null);
                    presenter.fetchRecipes();
                    isLoading = true;
                }
            }
        });

        recipesAdapter.addItem(null);
        presenter.onViewPrepared();
    }

    @Override
    public void updateRecipeList(List<Recipe> recipeList) {

        if (recipesAdapter.getItemCount() > 0)
            recipesAdapter.removeItem();

        if (recipesAdapter.getItemCount() == 0)
            recipesRecyclerView.scheduleLayoutAnimation();

        recipesAdapter.addItems(recipeList);

        isLoading = false;
        isScrollEnabled = true;
    }

    @Override
    public void showErrorInRecyclerView() {
        RecipesAdapter.LoadingMoreViewHolder loadingMoreViewHolder = (RecipesAdapter.LoadingMoreViewHolder) recipesRecyclerView.findViewHolderForAdapterPosition(recipesAdapter.getItemCount() - 1);
        if (loadingMoreViewHolder != null) {
            loadingMoreViewHolder.loading.setVisibility(View.GONE);
            loadingMoreViewHolder.retryButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLikeRecipeClick(int position) {
        presenter.likeTheRecipe(recipesAdapter.getItem(position));
    }

    @Override
    public void onRetryClick() {
        presenter.fetchRecipes();
    }

    @Override
    public void onShareClick(String sourceUrl) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sourceUrl);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share using..."));
    }

    @Override
    public void onIngredientsClick(String recipeId) {
        presenter.getSingleRecipe(recipeId);
    }

    @Override
    public void showIngredients(List<String> ingredients) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ingredients_list_view, null, false);
        ((TextView) view.findViewById(R.id.title)).setText(R.string.ingredients);
        ListView listView = (ListView) view.findViewById(R.id.ingredients_list);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.ingredients_list_item, ingredients));

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        dialog.show();
    }

    @OnClick(R.id.fab)
    public void openSearchActivity() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
