package com.vivekvishwanath.bitters.views;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.vivekvishwanath.bitters.R;
import com.vivekvishwanath.bitters.Utils.CocktailUtils;
import com.vivekvishwanath.bitters.adapters.IngredientListAdapter;
import com.vivekvishwanath.bitters.models.Cocktail;
import com.vivekvishwanath.bitters.models.Ingredient;
import com.vivekvishwanath.bitters.models.Ingredients;
import com.vivekvishwanath.bitters.mvvm.CocktailViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class CustomCocktailFragment extends Fragment {

    private EditText customCocktailName;
    private CheckBox showIngredientsBox;
    private EditText instructionsText;
    private Button saveButton;
    private ImageView cocktailImage;
    private FloatingActionButton addImageButton;

    private RecyclerView allIngredientsRecyclerView;
    private RecyclerView.LayoutManager allIngredientsLayoutManager;
    private IngredientListAdapter allIngredientsListAdapter;

    private RecyclerView selectedIngredientsRecyclerView;
    private RecyclerView.LayoutManager selectedIngredientsLayoutManager;
    public static IngredientListAdapter selectedIngredientsListAdapter;

    private CocktailViewModel viewModel;
    private Context context;
    public static ArrayList<Ingredient> selectedIngredients;

    public static final int IMAGE_REQUEST_CODE = 3;
    private int cocktail_id;

    public CustomCocktailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_cocktail, container, false);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedIngredients = new ArrayList<>(15);
        context = getActivity();
        viewModel = ViewModelProviders.of(getActivity()).get(CocktailViewModel.class);
        if (viewModel.getSelectedIngredients().getValue() == null) {
            viewModel.getSelectedIngredients().setValue(selectedIngredients);
        }

        showIngredientsBox = view.findViewById(R.id.show_ingredients_button);
        showIngredientsBox.setOnCheckedChangeListener(showBoxListener);

        customCocktailName = view.findViewById(R.id.custom_cocktail_name);

        addImageButton = view.findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(imageListener);

        cocktailImage = view.findViewById(R.id.custom_cocktail_image);
        cocktailImage.setOnClickListener(imageListener);
        if (viewModel.getCocktailImage().getValue() != null) {
            cocktailImage.setImageBitmap(viewModel.getCocktailImage().getValue());
            addImageButton.setVisibility(View.INVISIBLE);
        }

        instructionsText = view.findViewById(R.id.custom_cocktail_instructions_text);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonListener);

        allIngredientsRecyclerView = getView().findViewById(R.id.custom_cocktail_all_ingredients);
        allIngredientsRecyclerView.setHasFixedSize(true);
        allIngredientsLayoutManager = new GridLayoutManager(getContext(), 5);
        allIngredientsRecyclerView.setLayoutManager(allIngredientsLayoutManager);

        selectedIngredientsRecyclerView = view.findViewById(R.id.custom_cocktail_selected_ingredients);
        selectedIngredientsRecyclerView.setHasFixedSize(true);
        selectedIngredientsLayoutManager = new GridLayoutManager(getContext(), 3);
        selectedIngredientsRecyclerView.setLayoutManager(selectedIngredientsLayoutManager);

        viewModel.getAllIngredients().observe(getActivity(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> ingredients) {
                allIngredientsListAdapter = new IngredientListAdapter(ingredients, context, viewModel, true, false);
                allIngredientsRecyclerView.setAdapter(allIngredientsListAdapter);
            }
        });

        viewModel.getSelectedIngredients().observe(getActivity(), new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> ingredients) {
                selectedIngredientsListAdapter = new IngredientListAdapter(ingredients, context, viewModel, true, true);
                selectedIngredientsRecyclerView.setAdapter(selectedIngredientsListAdapter);
            }
        });
    }

    CompoundButton.OnCheckedChangeListener showBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                allIngredientsRecyclerView.setVisibility(View.VISIBLE);
            } else {
                allIngredientsRecyclerView.setVisibility(View.GONE);
            }
        }
    };

    View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkFields()) {
                cocktail_id = createId();
                Cocktail cocktail = new Cocktail(Integer.toString(cocktail_id));
                cocktail.setDrinkName(customCocktailName.getText().toString());
                Ingredients ingredients = CocktailUtils.createIngredientsObject(viewModel.getSelectedIngredients().getValue());
                cocktail.setIngredients(ingredients);
                cocktail.getIngredients().setIngredientsId(Integer.parseInt(cocktail.getDrinkId()));
                cocktail.setInstructions(instructionsText.getText().toString());
                cocktail.setPhotoUrl(storeImage(viewModel.getCocktailImage().getValue(), cocktail_id));
                viewModel.addCustomCocktail(cocktail);
            }
        }
    };

    View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            cocktail_id = createId();
            imageIntent.putExtra("id", cocktail_id);
            imageIntent.setType("image/*");
            getActivity().setResult(Activity.RESULT_OK, imageIntent);
            getActivity().startActivityForResult(imageIntent, IMAGE_REQUEST_CODE);
        }
    };

    private boolean checkFields() {
        if (TextUtils.isEmpty(customCocktailName.getText().toString())) {
            return false;
        } else if (TextUtils.isEmpty(instructionsText.getText().toString())) {
            return false;
        } else if (viewModel.getSelectedIngredients().getValue().size() < 2
                || viewModel.getSelectedIngredients().getValue().size() > 15) {
            return false;
        } else if (cocktailImage.getDrawable() == null) {
            return false;
        }
        return true;
    }

    private int createId() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        if (viewModel.getCustomIds().getValue().contains(num)) {
            createId();
        }
        return num;
    }

    private String storeImage(Bitmap bitmap, int cocktailId) {
        Bitmap image = bitmap;
        File directory = new File(context.getFilesDir(), "imageDir");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File myPath = new File(directory, cocktailId + ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            String uriPath = Uri.parse(myPath.getAbsolutePath()).toString();
            return uriPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
