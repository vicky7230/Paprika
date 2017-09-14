package com.vicky7230.eatit.ui.home.recipes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.BaseViewHolder;
import com.vicky7230.eatit.utils.GlideApp;
import com.vicky7230.eatit.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class RecipesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_DOUBLE_CLICKED = "action_like_image_button";

    public static final int VIEW_TYPE_LOADING = 100;
    public static final int VIEW_TYPE_ITEM = 200;

    public interface Callback {
        void onLikeRecipeClick(int position);

        void onRetryClick();

        void onShareClick(String sourceUrl);

        void onIngredientsClick(String recipeId);
    }

    private int tapCount = 0;
    private List<Recipe> recipeList;
    private Callback callback;

    public RecipesAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addItems(List<Recipe> recipeList) {

        List<Recipe> newRecipeList = new ArrayList<>();
        newRecipeList.addAll(this.recipeList);
        newRecipeList.addAll(recipeList);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RecipeDiffUtilCallback(this.recipeList, newRecipeList));
        this.recipeList.addAll(recipeList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItem(Recipe recipe) {
        recipeList.add(recipe);
        notifyItemInserted(recipeList.size() - 1);
    }

    public void removeItem() {
        recipeList.remove(recipeList.size() - 1);
        notifyItemRemoved(recipeList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return recipeList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false));
        else if (viewType == VIEW_TYPE_LOADING)
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_view_footer, parent, false));

        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof ProgressViewHolder) {
            if (!NetworkUtils.isNetworkConnected(((ProgressViewHolder) holder).itemView.getContext())) {
                ((ProgressViewHolder) holder).loading.setVisibility(View.GONE);
                ((ProgressViewHolder) holder).retryButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Recipe getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.recipe_title)
        TextView recipeTitleTextView;
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;
        @BindView(R.id.image_view_like)
        ImageView imageViewLike;
        @BindView(R.id.like_button)
        ImageView likeButton;
        @BindView(R.id.share_button)
        ImageView shareButton;
        @BindView(R.id.ingredients_button)
        ImageView ingredientsButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            final Recipe recipe = recipeList.get(position);

            if (recipe.getTitle() != null)
                recipeTitleTextView.setText(recipe.getTitle());

            if (recipe.getImageUrl() != null)
                GlideApp.with(itemView.getContext())
                        .load(recipe.getImageUrl())
                        .transition(withCrossFade())
                        .dontTransform()
                        .into(recipeImageView);

            if (recipe.getLiked()) {
                likeButton.setImageResource(R.drawable.ic_favorite_red_24dp);
            } else {
                likeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tapCount++;
                    if (tapCount == 1) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                if (tapCount == 1) {
                                    onSingleClick(recipe, itemView.getContext());
                                }
                                tapCount = 0;
                            }
                        }, 250);
                    } else if (tapCount == 2) {
                        tapCount = 0;
                        onDoubleClick(position, recipe);
                    }
                }
            });

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);
                    callback.onLikeRecipeClick(position);
                    recipe.setLiked(true);
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onShareClick(recipe.getSourceUrl());
                }
            });

            ingredientsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onIngredientsClick(recipe.getRecipeId());
                }
            });
        }

        @Override
        protected void clear() {
            recipeTitleTextView.setText("");
            recipeImageView.setImageDrawable(null);
            likeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    private void onDoubleClick(int position, Recipe recipe) {
        notifyItemChanged(position, ACTION_LIKE_IMAGE_DOUBLE_CLICKED);
        callback.onLikeRecipeClick(position);
        recipe.setLiked(true);
    }

    private void onSingleClick(Recipe recipe, Context context) {
        if (recipe.getSourceUrl() != null) {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    .build();
            customTabsIntent.launchUrl(context, Uri.parse(recipe.getSourceUrl()));
        }
    }

    public class ProgressViewHolder extends BaseViewHolder {

        @BindView(R.id.loading)
        LinearLayout loading;
        @BindView(R.id.retry_button)
        IconTextView retryButton;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            itemView.setLayoutParams(layoutParams);

            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.GONE);
                    callback.onRetryClick();
                }
            });

        }

        @Override
        protected void clear() {

        }
    }
}
