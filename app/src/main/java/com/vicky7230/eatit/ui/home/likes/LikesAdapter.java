package com.vicky7230.eatit.ui.home.likes;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.ui.base.BaseViewHolder;
import com.vicky7230.eatit.utils.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by vicky on 25/6/17.
 */

public class LikesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<LikedRecipe> likedRecipeList;
    private Callback callback;

    public LikesAdapter(List<LikedRecipe> likedRecipeList) {
        this.likedRecipeList = likedRecipeList;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addItems(List<LikedRecipe> likedRecipeList) {

        this.likedRecipeList.addAll(likedRecipeList);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {

        likedRecipeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, likedRecipeList.size());
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.onBind(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public LikedRecipe getItem(int position) {

        return likedRecipeList.get(position);
    }

    @Override
    public int getItemCount() {
        return likedRecipeList.size();
    }


    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.recipe_title)
        TextView recipeTitleTextView;
        @BindView(R.id.recipe_image)
        ImageView recipeImageView;
        @BindView(R.id.like_button)
        ImageView likeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(final int position) {
            super.onBind(position);

            final LikedRecipe likedRecipe = likedRecipeList.get(position);

            if (likedRecipe.getTitle() != null)
                recipeTitleTextView.setText(likedRecipe.getTitle());

            if (likedRecipe.getImageUrl() != null)
                GlideApp.with(itemView.getContext())
                        .load(likedRecipe.getImageUrl())
                        .transition(withCrossFade())
                        .centerCrop()
                        .into(recipeImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (likedRecipe.getImageUrl() != null) {

                        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                                .setShowTitle(true)
                                .setToolbarColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary))
                                .setSecondaryToolbarColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimaryDark))
                                .build();

                        customTabsIntent.launchUrl(itemView.getContext(), Uri.parse(likedRecipe.getSourceUrl()));
                    }
                }
            });

            likeButton.setImageResource(R.drawable.ic_favorite_red);
        }

        @Override
        protected void clear() {

            recipeTitleTextView.setText("");
            recipeImageView.setImageDrawable(null);

        }
    }

    public interface Callback {
        void onPopUpRemoveClick(LikedRecipe likedRecipe, int position);
    }
}
