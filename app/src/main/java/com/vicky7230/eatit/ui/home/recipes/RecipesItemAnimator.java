package com.vicky7230.eatit.ui.home.recipes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.vicky7230.eatit.R;

import java.util.List;

/**
 * Created by agrim on 18/8/17.
 */

public class RecipesItemAnimator extends DefaultItemAnimator {

    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AnticipateOvershootInterpolator ANTICIPATE_OVERSHOOT_INTERPOLATOR = new AnticipateOvershootInterpolator(1.0f);

    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state, @NonNull RecyclerView.ViewHolder viewHolder, int changeFlags, @NonNull List<Object> payloads) {
        if (changeFlags == FLAG_CHANGED) {
            for (Object payload : payloads) {
                if (payload instanceof String) {
                    return new RecipesItemHolderInfo((String) payload);
                }
            }
        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {

        if (preInfo instanceof RecipesItemHolderInfo) {
            RecipesItemHolderInfo recipesItemHolderInfo = (RecipesItemHolderInfo) preInfo;
            RecipesAdapter.RecipeViewHolder holder = (RecipesAdapter.RecipeViewHolder) newHolder;

            animateHeartButton(holder);
            if (RecipesAdapter.ACTION_LIKE_IMAGE_DOUBLE_CLICKED.equals(recipesItemHolderInfo.updateAction)) {
                animatePhotoLike(holder);
            }

        }

        return false;
    }


    private void animateHeartButton(final RecipesAdapter.RecipeViewHolder holder) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(holder.likeButton, "rotation", 0.0f, 360.0f);
        rotation.setDuration(800);
        rotation.setInterpolator(ANTICIPATE_OVERSHOOT_INTERPOLATOR);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.likeButton, "scaleX", 1.0f, 1.5f, 1.0f);
        scaleX.setDuration(800);
        scaleX.setInterpolator(ANTICIPATE_OVERSHOOT_INTERPOLATOR);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.likeButton, "scaleY", 1.0f, 1.5f, 1.0f);
        scaleY.setDuration(800);
        scaleY.setInterpolator(ANTICIPATE_OVERSHOOT_INTERPOLATOR);

        rotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (valueAnimator.getCurrentPlayTime() >= 500) {
                    holder.likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
                }
            }
        });

        animatorSet.playTogether(rotation, scaleX, scaleY);

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animator) {
                dispatchAnimationFinished(holder);
            }

        });

        animatorSet.start();

    }

    private void animatePhotoLike(final RecipesAdapter.RecipeViewHolder holder) {
        holder.imageViewLike.setVisibility(View.VISIBLE);

        holder.imageViewLike.setScaleY(0.0f);
        holder.imageViewLike.setScaleX(0.0f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleLikeIcon = ObjectAnimator.ofPropertyValuesHolder(holder.imageViewLike, PropertyValuesHolder.ofFloat("scaleX", 0.0f, 2.0f), PropertyValuesHolder.ofFloat("scaleY", 0.0f, 2.0f), PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f, 0.0f));
        scaleLikeIcon.setInterpolator(DECELERATE_INTERPOLATOR);
        scaleLikeIcon.setDuration(1000);

        ObjectAnimator scaleLikeBackground = ObjectAnimator.ofPropertyValuesHolder(holder.itemView, PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.95f, 1.0f), PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.95f, 1.0f));
        scaleLikeBackground.setInterpolator(DECELERATE_INTERPOLATOR);
        scaleLikeBackground.setDuration(600);

        animatorSet.playTogether(scaleLikeIcon, scaleLikeBackground);


        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetLikeAnimationState(holder);
                dispatchAnimationFinished(holder);
            }
        });
        animatorSet.start();
    }

    private void resetLikeAnimationState(RecipesAdapter.RecipeViewHolder holder) {
        holder.imageViewLike.setVisibility(View.INVISIBLE);
    }

    public static class RecipesItemHolderInfo extends ItemHolderInfo {
        public String updateAction;

        public RecipesItemHolderInfo(String updateAction) {
            this.updateAction = updateAction;
        }
    }
}
