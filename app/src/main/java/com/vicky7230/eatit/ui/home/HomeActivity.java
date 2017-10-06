package com.vicky7230.eatit.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.BaseActivity;
import com.vicky7230.eatit.utils.BottomNavigationViewHelper;
import com.vicky7230.eatit.utils.GlideApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class HomeActivity extends BaseActivity implements HomeMvpView, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;
    @Inject
    ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.bottom_sheet)
    RelativeLayout bottomSheetViewGroup;
    @BindView(R.id.arrow_down)
    AppCompatImageView arrowDownImageView;
    @BindView(R.id.recipe_title_bottom_sheet)
    AppCompatTextView recipeTitleBottomSheet;
    @BindView(R.id.recipe_publisher_bottom_sheet)
    AppCompatTextView recipePublisherBottomSheet;
    @BindView(R.id.recipe_social_rank_bottom_sheet)
    AppCompatTextView recipeSocialRankBottomSheet;
    @BindView(R.id.recipe_image_bottom_sheet)
    AppCompatImageView recipeImageViewBottomSheet;
    @BindView(R.id.scrim)
    View scrim;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        init();
    }

    private void init() {

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.recipes:
                        viewPager.setCurrentItem(0, true);
                        return true;
                    case R.id.imagga:
                        viewPager.setCurrentItem(1, true);
                        return true;
                    case R.id.likes:
                        viewPager.setCurrentItem(2, true);
                        return true;
                    case R.id.settings:
                        viewPager.setCurrentItem(3, true);
                        return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetViewGroup);
        hideBottomSheet();
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    @OnClick(R.id.arrow_down)
    public void onArrowDownClick(View view) {
        hideBottomSheet();
    }

    @OnClick(R.id.scrim)
    public void onScrimClick(View view) {
        hideBottomSheet();
    }

    private void showBottomSheet() {
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        scrim.setVisibility(View.VISIBLE);
    }

    private void hideBottomSheet() {
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        scrim.setVisibility(View.GONE);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showBottomSheet(Recipe recipe) {
        showBottomSheet();
        if (recipe != null) {
            if (recipe.getTitle() != null)
                recipeTitleBottomSheet.setText(recipe.getTitle());
            if (recipe.getPublisher() != null)
                recipePublisherBottomSheet.setText("Publisher : " + recipe.getPublisher());
            if (recipe.getSocialRank() != null)
                recipeSocialRankBottomSheet.setText("Social Rank : " + recipe.getSocialRank().toString());
            if (recipe.getImageUrl() != null)
                GlideApp.with(this)
                        .load(recipe.getImageUrl())
                        .transition(withCrossFade())
                        .centerCrop()
                        .into(recipeImageViewBottomSheet);
        }
    }

    @Override
    public void onBackPressed() {
        switch (bottomSheetBehavior.getState()) {
            case BottomSheetBehavior.STATE_EXPANDED:
                hideBottomSheet();
                break;
            default:
                super.onBackPressed();
        }
    }
}
