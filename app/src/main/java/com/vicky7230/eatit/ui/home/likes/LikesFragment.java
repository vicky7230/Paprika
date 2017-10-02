package com.vicky7230.eatit.ui.home.likes;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.ui.base.BaseFragment;
import com.vicky7230.eatit.ui.home.recipes.ItemOffsetDecoration;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikesFragment extends BaseFragment implements LikesMvpView, LikesAdapter.Callback {

    @Inject
    LikesMvpPresenter<LikesMvpView> presenter;
    @Inject
    LikesAdapter likesAdapter;
    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @BindView(R.id.favourites_recycler_view)
    RecyclerView favouritesRecyclerView;

    public static LikesFragment newInstance() {
        Bundle args = new Bundle();
        LikesFragment fragment = new LikesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_likes, container, false);
        presenter.onAttach(this);
        ButterKnife.bind(this, view);
        likesAdapter.setCallback(this);
        return view;
    }

    @Override
    protected void setUp(View view) {

        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favouritesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        favouritesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favouritesRecyclerView.addItemDecoration(new ItemOffsetDecoration(40));
        favouritesRecyclerView.setAdapter(likesAdapter);

        presenter.loadAllFavouriteRecipes();
    }

    @Override
    public void updateFavouriteRecipeList(List<LikedRecipe> likedRecipeList) {

        likesAdapter.addItems(likedRecipeList);
    }

    @Override
    public void removeRecipeFromFavouriteListView(int position) {

        likesAdapter.removeItem(position);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onPopUpRemoveClick(LikedRecipe likedRecipe, int position) {

        presenter.removeRecipeFromFavourites(likedRecipe, position);
    }
}
