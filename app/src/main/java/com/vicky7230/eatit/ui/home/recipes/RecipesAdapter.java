package com.vicky7230.eatit.ui.home.recipes;

import android.content.Context;
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
import com.vicky7230.eatit.utils.GlideApp;
import com.vicky7230.eatit.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_DOUBLE_CLICKED = "action_like_image_button";

    public static final int TYPE_LOADING_MORE = 1;
    public static final int TYPE_RECIPE = -1;

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
        return recipeList.get(position) == null ? TYPE_LOADING_MORE : TYPE_RECIPE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Recipe getItem(int position) {
        if (position != RecyclerView.NO_POSITION)
            return recipeList.get(position);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_RECIPE:
                return createRecipeViewHolder(parent);
            case TYPE_LOADING_MORE:
                return createLoadingMoreViewHolder(parent);
        }
        return null;
    }

    private RecyclerView.ViewHolder createRecipeViewHolder(ViewGroup parent) {
        final RecipeViewHolder recipeViewHolder = new RecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false));

        recipeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Recipe recipe = getItem(recipeViewHolder.getAdapterPosition());
                if (recipe != null) {
                    tapCount++;
                    if (tapCount == 1) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                if (tapCount == 1) {
                                    onSingleClick(recipe, recipeViewHolder.itemView.getContext());
                                }
                                tapCount = 0;
                            }
                        }, 250);
                    } else if (tapCount == 2) {
                        tapCount = 0;
                        onDoubleClick(recipeViewHolder.getAdapterPosition(), recipe);
                    }
                }
            }
        });

        recipeViewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Recipe recipe = getItem(recipeViewHolder.getAdapterPosition());
                if (recipe != null) {
                    notifyItemChanged(recipeViewHolder.getAdapterPosition(), ACTION_LIKE_BUTTON_CLICKED);
                    callback.onLikeRecipeClick(recipeViewHolder.getAdapterPosition());
                    recipe.setLiked(true);
                }
            }
        });

        recipeViewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Recipe recipe = getItem(recipeViewHolder.getAdapterPosition());
                if (recipe != null) {
                    callback.onShareClick(recipe.getSourceUrl());
                }
            }
        });

        recipeViewHolder.ingredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Recipe recipe = getItem(recipeViewHolder.getAdapterPosition());
                if (recipe != null) {
                    callback.onIngredientsClick(recipe.getRecipeId());
                }
            }
        });

        return recipeViewHolder;
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
                    .addDefaultShareMenuItem()
                    .build();
            customTabsIntent.launchUrl(context, Uri.parse(recipe.getSourceUrl()));
        }
    }

    private RecyclerView.ViewHolder createLoadingMoreViewHolder(ViewGroup parent) {
        final LoadingMoreViewHolder loadingMoreViewHolder = new LoadingMoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_view_footer, parent, false));

        loadingMoreViewHolder.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingMoreViewHolder.loading.setVisibility(View.VISIBLE);
                loadingMoreViewHolder.retryButton.setVisibility(View.GONE);
                callback.onRetryClick();
            }
        });

        return loadingMoreViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_RECIPE:
                ((RecipeViewHolder) holder).onBind(position);
                break;
            case TYPE_LOADING_MORE:
                ((LoadingMoreViewHolder) holder).onBind(position);
                break;
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof RecipeViewHolder) {
            ((RecipeViewHolder) holder).recipeTitleTextView.setText("");
            ((RecipeViewHolder) holder).recipeImageView.setImageDrawable(null);
            ((RecipeViewHolder) holder).likeButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof LoadingMoreViewHolder) {
            if (!NetworkUtils.isNetworkConnected(((LoadingMoreViewHolder) holder).itemView.getContext())) {
                ((LoadingMoreViewHolder) holder).loading.setVisibility(View.GONE);
                ((LoadingMoreViewHolder) holder).retryButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

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

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(final int position) {
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
        }
    }

    public class LoadingMoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loading)
        LinearLayout loading;
        @BindView(R.id.retry_button)
        IconTextView retryButton;

        public LoadingMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setFullSpan(true);
            itemView.setLayoutParams(layoutParams);
        }
    }
}
