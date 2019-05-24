package com.vivekvishwanath.bitters.views;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.Utils.CocktailUtils;
import com.vivekvishwanath.bitters.adapters.IngredientListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ViewCocktailFragment extends DialogFragment {

    private Cocktail cocktail;
    private TextView cocktailName;
    private ImageView cocktailImage;
    private LinearLayout instructionsLayout;
    private ConstraintLayout viewCocktailParent;
    private CheckBox alcoholicCheckbox;
    private ProgressBar progressBar;
    private FloatingActionButton backButton;
    ArrayList<Ingredient> ingredientList;
    ArrayList<String> instructionsList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private IngredientListAdapter listAdapter;

    private Context context;
    private CocktailViewModel viewModel;

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
        context = getActivity();
        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);

        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        viewCocktailParent = view.findViewById(R.id.view_cocktail_layout);

        cocktailName = view.findViewById(R.id.view_cocktail_name);
        cocktailName.setText(cocktail.getDrinkName());

        progressBar = view.findViewById(R.id.view_cocktail_progress_bar);
        alcoholicCheckbox = view.findViewById(R.id.view_cocktail_checkbox_alcoholic);
        if (cocktail.getIsAlcoholic() != null && cocktail.getIsAlcoholic().toLowerCase().equals("non alcoholic")) {
            alcoholicCheckbox.setChecked(false);
        }
        cocktailImage = view.findViewById(R.id.view_cocktail_image);
        Picasso.get().load(cocktail.getPhotoUrl()).into(cocktailImage, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
        File directory = new File(context.getFilesDir(), "imageDir");
        if (directory.exists()) {
            File f = new File(directory, Integer.parseInt(cocktail.getDrinkId()) + ".png");
            if (f.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
                    cocktailImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        ingredientList = CocktailUtils.convertCocktailIngredients(cocktail);

        recyclerView = view.findViewById(R.id.recycler_view_ingredients);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        listAdapter = new IngredientListAdapter(ingredientList, context, viewModel, false, false);
        recyclerView.setAdapter(listAdapter);

        instructionsLayout = view.findViewById(R.id.instructions_layout);
        instructionsList = CocktailUtils.getInstructionList(cocktail);
        addInstructions();
    }

    View.OnLongClickListener favoriteListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    };

    private void addInstructions() {
        for (int i = 0; i < instructionsList.size(); i++) {
            TextView textView = new TextView(context);
            textView.setText(instructionsList.get(i));
            textView.setTextSize(16);
            textView.setPadding(5,5,5,5);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            instructionsLayout.addView(textView);
         }
    }


}
