package com.vivekvishwanath.bitters.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;
import com.vivekvishwanath.bitters.views.ViewCocktailFragment;

import java.io.File;
import java.util.ArrayList;

public class CocktailListAdapter extends RecyclerView.Adapter<CocktailListAdapter.ViewHolder> {

    private ArrayList<Cocktail> cocktailList;
    private Context context;
    private CocktailViewModel viewModel;
    private boolean canFavorite;

    public CocktailListAdapter(ArrayList<Cocktail> cocktailList, CocktailViewModel viewModel
            ,boolean canFavorite) {
        this.cocktailList = cocktailList;
        this.viewModel = viewModel;
        this.canFavorite = canFavorite;
    }

    @NonNull
    @Override
    public CocktailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cocktail_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CocktailListAdapter.ViewHolder holder, final int position) {
        final Cocktail cocktail = cocktailList.get(position);
        holder.cocktailName.setText(cocktail.getDrinkName());
        viewModel.getFavoriteIds().observe((LifecycleOwner)context
                , new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> ids) {
                if (ids.contains(cocktailList.get(position).getDrinkId())) {
                    holder.star.setImageResource(R.drawable.ic_filled_star);
                } else {
                    holder.star.setImageResource(R.drawable.ic_empty_star);
                }
            }
        });

        holder.cocktailParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                DialogFragment dialogFragment = ViewCocktailFragment.newInstance(cocktailList.get(position));
                dialogFragment.show(fragmentManager, "cocktail");
            }
        });

        if (!canFavorite) {
            holder.progressBar.setVisibility(View.GONE);
            File directory = new File(context.getFilesDir(), "imageDir");
            if (directory.exists()) {
                File f = new File(directory, Integer.parseInt(cocktail.getDrinkId()) + ".png");
                if (f.exists()) {
                        // Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                        // holder.cocktailImage.setImageBitmap(bitmap);
                        Picasso.get().load(f)
                                .resize(700, 700).onlyScaleDown()
                                .into(holder.cocktailImage);
                }
            }

            holder.cocktailParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    boolean deleted = false;
                    File directory = new File(context.getFilesDir(), "imageDir");
                    if (directory.exists()) {
                        File myPath = new File(directory, cocktail.getDrinkId() + ".png");
                        deleted = myPath.delete();
                    }
                    if (deleted) {
                        holder.cocktailParent.setBackgroundResource(android.R.color.holo_red_dark);
                        viewModel.deleteCustomCocktail(cocktail);
                        viewModel.getCustomCocktails();
                    }
                    return true;
                }
            });
        }
        if (canFavorite) {
            Picasso.get()
                    .load(cocktail.getPhotoUrl())
                    .into(holder.cocktailImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
            holder.cocktailParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (viewModel.getFavoriteIds().getValue().contains(cocktail.getDrinkId())) {
                        ArrayList<String> newIds = viewModel.getFavoriteIds().getValue();
                        newIds.remove(cocktail.getDrinkId());
                        viewModel.updateFavoriteIds(newIds);
                        holder.star.setImageResource(R.drawable.ic_empty_star);
                    } else {
                        ArrayList<String> newIds = viewModel.getFavoriteIds().getValue();
                        newIds.add(cocktail.getDrinkId());
                        viewModel.updateFavoriteIds(newIds);
                        holder.star.setImageResource(R.drawable.ic_filled_star);
                    }
                    return true;
                }
            });
        }
        //setEnterAnimation(holder.cocktailParent);
    }

    public void setEnterAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        if (cocktailList != null) {
            return cocktailList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cocktailName;
        private ImageView cocktailImage;
        private CardView cocktailParent;
        private ImageView star;
        private ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            cocktailName = view.findViewById(R.id.cocktail_card_name);
            cocktailImage = view.findViewById(R.id.cocktail_card_image);
            cocktailParent = view.findViewById(R.id.cocktail_card_parent);
            star = view.findViewById(R.id.cocktail_card_star);
            progressBar = view.findViewById(R.id.cocktail_card_progress_bar);
        }
    }
}
