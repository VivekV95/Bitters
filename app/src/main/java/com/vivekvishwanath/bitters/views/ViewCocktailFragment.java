package com.vivekvishwanath.bitters.views;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.models.Cocktail;

public class ViewCocktailFragment extends DialogFragment {

    private Cocktail cocktail;
    private TextView cocktailName;
    private ImageView cocktailImage;

    public ViewCocktailFragment() {
        // Required empty public constructor
    }

    public static ViewCocktailFragment newInstance(Cocktail cocktail) {
        Bundle args = new Bundle();
        args.putSerializable(Cocktail.SERIALIZABLE_OCCKTAIL_KEY, cocktail);
        ViewCocktailFragment fragment = new ViewCocktailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        cocktail = (Cocktail) getArguments().getSerializable(Cocktail.SERIALIZABLE_OCCKTAIL_KEY);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_cocktail, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        cocktailName = view.findViewById(R.id.view_cocktail_name);
        cocktailImage = view.findViewById(R.id.view_cocktail_image);
        Picasso.get().load(cocktail.getPhotoUrl()).into(cocktailImage);
    }
}
