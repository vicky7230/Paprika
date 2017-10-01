package com.vicky7230.eatit.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HomeActivity extends BaseActivity implements HomeMvpView, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;
    @Inject
    ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private final int[] TAB_ICONS_UNSELECTED = {
            R.drawable.ic_recipes_unselected_24dp,
            R.drawable.ic_camera_unselected_24dp,
            R.drawable.ic_likes_unselected_24dp,
            R.drawable.ic_settings_unselected_24dp
    };
    private final int[] TAB_ICONS_SELECTED = {
            R.drawable.ic_recipes_selected_24dp,
            R.drawable.ic_camera_selected_24dp,
            R.drawable.ic_likes_selected_24dp,
            R.drawable.ic_settings_selected_24dp
    };

    private final int[] TAB_INDICATORS = {
            R.id.tab_indicator_1,
            R.id.tab_indicator_2,
            R.id.tab_indicator_3,
            R.id.tab_indicator_4
    };

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

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabLayout.getTabCount(); ++i) {
                    tabLayout.getTabAt(i).setIcon(
                            i != position ?
                                    TAB_ICONS_UNSELECTED[i] : TAB_ICONS_SELECTED[i]
                    );

                    if (i == position)
                        findViewById(TAB_INDICATORS[i]).setVisibility(View.VISIBLE);
                    else
                        findViewById(TAB_INDICATORS[i]).setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); ++i) {
            tabLayout.getTabAt(i).setIcon(
                    i != viewPager.getCurrentItem() ?
                            TAB_ICONS_UNSELECTED[i] : TAB_ICONS_SELECTED[i]
            );

            if (i == viewPager.getCurrentItem())
                findViewById(TAB_INDICATORS[i]).setVisibility(View.VISIBLE);
            else
                findViewById(TAB_INDICATORS[i]).setVisibility(View.GONE);
        }
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
}
