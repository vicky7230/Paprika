package com.vicky7230.eatit.ui.home.likes;

import android.net.Uri;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.BaseViewHolder;
import com.vicky7230.eatit.ui.home.recipes.RecipesAdapter;
import com.vicky7230.eatit.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class LikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LikedRecipe> likedRecipeList;
    private Callback callback;

    public LikesAdapter(List<LikedRecipe> likedRecipeList) {
        this.likedRecipeList = likedRecipeList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addItems(List<LikedRecipe> likedRecipeList) {

        List<LikedRecipe> newLikedRecipeList = new ArrayList<>();
        newLikedRecipeList.addAll(this.likedRecipeList);
        newLikedRecipeList.addAll(likedRecipeList);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new LikedRecipeDiffUtilCallback(this.likedRecipeList, newLikedRecipeList));
        this.likedRecipeList.addAll(likedRecipeList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void removeItem(int position) {

        likedRecipeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, likedRecipeList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecipeViewHolder recipeViewHolder = new RecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false));
        recipeViewHolder.recipeImageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LikedRecipe likedRecipe = getItem(recipeViewHolder.getAdapterPosition());
                if (likedRecipe != null) {
                    if (likedRecipe.getImageUrl() != null) {
                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .setShowTitle(true)
                                .setToolbarColor(ContextCompat.getColor(recipeViewHolder.itemView.getContext(), R.color.color_primary))
                                .setSecondaryToolbarColor(ContextCompat.getColor(recipeViewHolder.itemView.getContext(), R.color.color_primary_dark))
                                .build();
                        customTabsIntent.launchUrl(recipeViewHolder.itemView.getContext(), Uri.parse(likedRecipe.getSourceUrl()));
                    }
                }
            }
        });
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecipeViewHolder) holder).onBind(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public LikedRecipe getItem(int position) {
        if (position != RecyclerView.NO_POSITION)
            return likedRecipeList.get(position);
        else
            return null;
    }

    @Override
    public int getItemCount() {
        return likedRecipeList.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof RecipeViewHolder) {
            ((RecipeViewHolder) holder).recipeTitleTextView.setText("");
            ((RecipeViewHolder) holder).recipeImageView.setImageDrawable(null);
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_image_card)
        CardView recipeImageCardView;
        @BindView(R.id.recipe_title)
        TextView recipeTitleTextView;
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;
        @BindView(R.id.like_button)
        ImageView likeButton;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(final int position) {

            final LikedRecipe likedRecipe = likedRecipeList.get(position);

            if (likedRecipe.getTitle() != null)
                recipeTitleTextView.setText(likedRecipe.getTitle());

            if (likedRecipe.getImageUrl() != null)
                GlideApp.with(itemView.getContext())
                        .load(likedRecipe.getImageUrl())
                        .transition(withCrossFade())
                        .centerCrop()
                        .into(recipeImageView);

            likeButton.setImageResource(R.drawable.ic_favorite_higlighted_24dp);
        }

    }

    public interface Callback {
        void onPopUpRemoveClick(LikedRecipe likedRecipe, int position);
    }
}
